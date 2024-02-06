package com.pritimedia.cryptoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pritimedia.cryptoapp.api.APIFactory
import com.pritimedia.cryptoapp.ui.theme.CryptoAppTheme
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : ComponentActivity() {
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoAppTheme {
            }
        }
        val disposable = APIFactory.apiService.getFullPriceList(fSyms = "ETH,BTC")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d("TEST_OF_LOADING_DATA", it.toString()) },
                { it.message?.let { that -> Log.d("TEST_OF_LOADING_DATA", that) } },
            )
        compositeDisposable.add(disposable)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        compositeDisposable.dispose()
    }
}