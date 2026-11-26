
package com.example.rewear.data.local

import androidx.room.TypeConverter
import org.json.JSONArray
import java.util.Date

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

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}
