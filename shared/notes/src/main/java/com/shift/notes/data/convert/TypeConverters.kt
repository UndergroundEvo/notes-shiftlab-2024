package com.shift.notes.data.convert

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverters {
    @TypeConverter
    fun fromTagList(tags: List<String>): String {
        return Gson().toJson(tags)
    }

    @TypeConverter
    fun toTagList(tagsString: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(tagsString, listType)
    }
}