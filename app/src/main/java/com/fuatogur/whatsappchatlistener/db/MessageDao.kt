package com.fuatogur.whatsappchatlistener.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.fuatogur.whatsappchatlistener.model.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY created_at DESC")
    suspend fun getAll(): List<Message>

    @Insert
    suspend fun insertAll(vararg message: Message)

    @Delete
    suspend fun delete(message: Message)

    @Query("DELETE FROM messages")
    suspend fun deleteAll()
}