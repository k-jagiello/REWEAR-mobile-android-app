
package com.example.rewear.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "locations",
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
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val user_id: Long,

    val name: String,

    val latitude: Double,

    val longitude: Double,

    val photos: ArrayList<String> = arrayListOf(),

    val video: ArrayList<String> = arrayListOf(),

    val audio: ArrayList<String> = arrayListOf()
)
