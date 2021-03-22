package com.example.nasatestproject.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "nasa_posts")
data class NasaPostAsString(
    @PrimaryKey(autoGenerate = false)
    val id: Long?,
    @ColumnInfo(name = "content")
    val content: String?
) : Serializable
