package com.ktpractice.flow.profile.view


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ktpractice.R
import com.ktpractice.model.Person

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KEY_PERSON = "EXTRA_KEY_PERSON"
    }

    private var mPerson: Person? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        initData()
    }

    private fun initData() {
        mPerson = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_KEY_PERSON, Person::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_KEY_PERSON) as Person?
        }

        // TODO: 到此
        Log.d("", "")
    }
}