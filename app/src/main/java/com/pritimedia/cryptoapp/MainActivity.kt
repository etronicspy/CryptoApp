package com.pritimedia.cryptoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pritimedia.cryptoapp.ui.theme.CryptoAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: CoinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.loadData()
        viewModel.priceList.observe(
            this
        ) { Log.d("TEST_OF_LOADING_DATA", "Success in MainActivity: $it") }
        setContent {
            CryptoAppTheme {
            }
        }
    }
}