package com.pritimedia.cryptoapp.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CryptoCurrencyPriceInfo(
    @SerializedName("RAW")
    @Expose
    private val cryptoCurrencyPriceInfo: JsonObject? = null
)