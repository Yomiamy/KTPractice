package com.ktpractice.flow.main.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.ktpractice.R
import com.ktpractice.databinding.ComponentUserIconImageviewBinding

class UserIconImageView : LinearLayout {

    var imgSrc:String = ""
        set(imgSrc) {
            if (!imgSrc.isNullOrEmpty()) {
                Glide.with(context).load(imgSrc).centerCrop().into(mBinding.ivUserIcon)
            }
        }

    private lateinit var mBinding: ComponentUserIconImageviewBinding

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserIconImageView)
        initView(typedArray)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserIconImageView, defStyle, 0)
        initView(typedArray)
    }

    private fun initView(typedArray: TypedArray) {
        mBinding = ComponentUserIconImageviewBinding.inflate(LayoutInflater.from(context), this, true).apply {
            val imgSrc = typedArray.getString(R.styleable.UserIconImageView_imgSrc)
            val imgSize = typedArray.getDimensionPixelSize(R.styleable.UserIconImageView_imgSize, resources.getDimensionPixelSize(R.dimen.person_avatar_h))

            this@UserIconImageView.imgSrc = imgSrc ?: ""
            this.ivUserIcon.layoutParams = LayoutParams(imgSize, imgSize)
        }
        typedArray.recycle()
    }
}