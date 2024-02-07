package com.pritimedia.cryptoapp.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CoinData(
    @SerializedName("Data")
    @Expose
    val data: List<Datum>? = null
)