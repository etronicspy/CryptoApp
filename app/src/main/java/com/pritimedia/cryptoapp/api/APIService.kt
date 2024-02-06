package com.pritimedia.cryptoapp.api

import com.pritimedia.cryptoapp.pojo.CryptoCurrencies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val DEFAULT_CURRENCY = "USD"
        private const val API_KEY = "2a4f5529c9b116305bc5637a12554e670d8615e009c17fc068e3510bd9b711b5"

    }

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = DEFAULT_CURRENCY,
    ): Single<CryptoCurrencies>
}