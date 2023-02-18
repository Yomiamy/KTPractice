package com.ktpractice.flow.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
// import com.ktpractice.flow.main.MainFlowContract.IMainFlowView

class MainViewModelFactory<K>(private val params:K): ViewModelProvider.Factory  where K: android.content.Context, K: androidx.lifecycle.LifecycleOwner {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(params) as T
    }

}