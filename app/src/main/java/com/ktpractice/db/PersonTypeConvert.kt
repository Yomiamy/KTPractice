package com.ktpractice.db

import android.text.TextUtils
import androidx.room.TypeConverter
import java.lang.StringBuilder
import java.util.*

class PersonTypeConvert {
    @TypeConverter
    fun strListToStr(list: List<String>?): String {
        if (list == null || list.isEmpty()) return ""

        val strBuilder = StringBuilder()
        for (str in list) {
            strBuilder.append(str).append(",")
        }
        return strBuilder.deleteCharAt(strBuilder.length - 1).toString()
    }

    @TypeConverter
    fun strToStrList(str: String): List<String> {
        return if (!TextUtils.isEmpty(str)) str.split(",") else Collections.emptyList()
    }

}