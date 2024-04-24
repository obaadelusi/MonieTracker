package com.oba.monietracker.data.utlities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oba.monietracker.data.models.Category
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {
    private val moshi: Moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        if(value == null) return null

        val listType = Types.newParameterizedType(List::class.java, String::class.javaObjectType)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(listType)

        return adapter.fromJson(value)
    }

    @TypeConverter
    fun listToString(list: List<String>?): String? {
        if(list == null) return null

        val listType = Types.newParameterizedType(List::class.java, String::class.javaObjectType)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(listType)

        return adapter.toJson(list)
    }

    private val gson = Gson()

    @TypeConverter
    fun fromCategory(category: Category?): String {
        return gson.toJson(category)
    }

    @TypeConverter
    fun toCategory(categoryString: String): Category {
        val type = object : TypeToken<Category>() {}.type
        return gson.fromJson(categoryString, type)
    }
}