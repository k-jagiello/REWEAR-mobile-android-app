
package com.example.rewear.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "clothes",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"])
    ]
)
data class ClothingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val user_id: Long,

    val name: String,

    val photo_uri: String? = null,

    val category: String,

    val style: String,

    val main_color: String,

    val colors: ArrayList<String> = arrayListOf(),

    val spring: Boolean = false,

    val summer: Boolean = false,

    val autumn: Boolean = false,

    val winter: Boolean = false,

    val rating: Int,

    val is_clean: Boolean = true,

    val is_active: Boolean = true
)

