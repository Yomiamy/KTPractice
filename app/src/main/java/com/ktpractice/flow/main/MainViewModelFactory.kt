package com.ktpractice.flow.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ktpractice.flow.main.MainFlowContract.IMainFlowView

class MainViewModelFactory(val mView:MainActivity): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(mView) as T
    }

}