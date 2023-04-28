package com.ktpractice.utils

import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.widget.TextView



object Utils {
    fun setColor(view: TextView, fulltext: String, subtext: String, color: Int) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE)
        val str = view.text as Spannable
        val i = fulltext.indexOf(subtext)

        str.setSpan(
            ForegroundColorSpan(color),
            i,
            i + subtext.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    // TODO: For test
    fun validLogin(account: String, pw: String): Boolean {
        if (account.length < 6) {
            return false
        } else if (pw.length < 8) {
            return false
        }
        return true
    }
}