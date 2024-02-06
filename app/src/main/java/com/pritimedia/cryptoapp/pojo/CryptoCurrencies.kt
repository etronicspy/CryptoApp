package com.pritimedia.cryptoapp.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CryptoCurrencies(
    @SerializedName("Data")
    @Expose
    val data: List<Datum>? = null
)