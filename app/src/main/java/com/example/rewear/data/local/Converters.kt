
package com.example.rewear.data.local

import androidx.room.TypeConverter
import org.json.JSONArray

class Converters {

    @TypeConverter
    fun fromArrayList(value: ArrayList<String>): String {
        return JSONArray(value).toString()
    }

    @TypeConverter
    fun toArrayList(value: String): ArrayList<String> {
        val jsonArray = JSONArray(value)
        val result = arrayListOf<String>()

        for (i in 0 until jsonArray.length()) {
            result.add(jsonArray.getString(i))
        }

        return result
    }
}
