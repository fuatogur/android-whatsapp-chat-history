package com.fuatogur.whatsappchatlistener.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val from: String,
    val message: String,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
)