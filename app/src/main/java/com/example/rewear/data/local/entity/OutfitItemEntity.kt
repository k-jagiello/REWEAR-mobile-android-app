
package com.example.rewear.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "outfit_items",

    primaryKeys = [
        "outfit_id",
        "clothing_id"
    ],

    foreignKeys = [
        ForeignKey(
            entity = OutfitEntity::class,
            parentColumns = ["id"],
            childColumns = ["outfit_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ClothingEntity::class,
            parentColumns = ["id"],
            childColumns = ["clothing_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [
        Index(value = ["clothing_id"])
    ]
)
data class OutfitItemEntity(
    val outfit_id: Long,

    val clothing_id: Long
)
