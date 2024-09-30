package com.android.goally.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson

class TagConverter {
    @TypeConverter
    fun fromTagList(list: List<String>?): String? {
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toTagList(data: String?): List<String>? {
        return data?.let { Gson().fromJson(it, Array<String>::class.java).toList() }
    }
}