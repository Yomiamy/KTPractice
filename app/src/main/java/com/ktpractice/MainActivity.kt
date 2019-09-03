package com.ktpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.test.api.ApiInstMgr
import com.ktpractice.api.interfaces.IApi
import com.ktpractice.model.Person
import com.ktpractice.response.BaseResponse
import com.ktpractice.utils.ConstantUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var mDispose: CompositeDisposable
    private var mDataList: List<Person>? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDispose = CompositeDisposable()
        val api = ApiInstMgr.getInstnace(this, ConstantUtils.Api.SERVER_DOMAIN, IApi::class.java)
        api?.search("rangers", 0)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<BaseResponse<Person>> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {
                    mDispose.add(d)
                }

                override fun onNext(t: BaseResponse<Person>) {
                    mDataList = t.results
                    Log.d("Test", t.results?.toString() + " : " + t.results?.get(0)?.name)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        mDispose.clear()
    }
}
