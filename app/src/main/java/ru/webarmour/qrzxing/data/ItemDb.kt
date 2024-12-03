package ru.webarmour.qrzxing.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("items")
data class ItemDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val numberQR: String,
    val imagePath: String = ""
)
