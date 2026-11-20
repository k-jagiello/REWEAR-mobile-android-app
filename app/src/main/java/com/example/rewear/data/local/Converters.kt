
package com.example.rewear.data.local

import androidx.room.TypeConverter
import com.example.rewear.data.local.entity.ClothingEntity
import com.example.rewear.ui.screens.ClothingItem
import com.example.rewear.ui.screens.ClothingStatus
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

    @TypeConverter
    fun toClothingItem(value: ClothingEntity): ClothingItem{
        val computedStatus = when {
            !value.is_active -> ClothingStatus.INACTIVE
            !value.is_clean -> ClothingStatus.TO_WASH
            else -> ClothingStatus.CLEAN_ACTIVE
        }

        return ClothingItem(
            name = value.name,
            image = value.photo_uri,
            status = computedStatus
        )
    }
}
