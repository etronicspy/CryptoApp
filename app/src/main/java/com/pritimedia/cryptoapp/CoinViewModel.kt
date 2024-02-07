package com.pritimedia.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.pritimedia.cryptoapp.api.APIFactory
import com.pritimedia.cryptoapp.database.AppDatabase
import com.pritimedia.cryptoapp.pojo.CoinPriceInfo
import com.pritimedia.cryptoapp.pojo.CoinPriceInfoRawData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()
    val priceList = db.coinPriceInfoDao().getPriceList()
    fun loadData() {
        val disposable = APIFactory.apiService.getTopCoinsInfo(limit = 1).map {
            it.data?.map { item -> item.coinInfo?.name }?.joinToString(
                ","
            ) ?: ""
        }
            .flatMap {
                APIFactory.apiService.getFullPriceList(fSyms = it)
            }
            .map { getPriceInfoRawData(it) }
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    db.coinPriceInfoDao().insertPriceList(it)
                    Log.d("TEST_OF_LOADING_DATA", "Success: $it")
                },
                { it.message?.let { that -> Log.d("TEST_OF_LOADING_DATA", "Failure: $that") } },
            )
        compositeDisposable.add(disposable)
    }

    private fun getPriceInfoRawData(coinPriceInfoRawData: CoinPriceInfoRawData): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java,
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}