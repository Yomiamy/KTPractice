package com.imformation_checker.flow.profile.view


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imformation_checker.databinding.ActivityProfileBinding
import com.imformation_checker.model.Person

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KEY_PERSON = "EXTRA_KEY_PERSON"
    }

    private var mPerson: Person? = null
    private lateinit var mBinding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initData()
    }

    private fun initData() {
        mPerson = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_KEY_PERSON, Person::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_KEY_PERSON) as Person?
        }

        mBinding.uivUserIcon.imgSrc = if (!mPerson!!.avatar.isNullOrEmpty()) mPerson!!.avatar!! else mPerson!!.url!!
        mBinding.tvName.text = mPerson!!.name
    }
}