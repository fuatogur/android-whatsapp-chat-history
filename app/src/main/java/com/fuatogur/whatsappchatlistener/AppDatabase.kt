package com.fuatogur.whatsappchatlistener

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fuatogur.whatsappchatlistener.db.MessageDao
import com.fuatogur.whatsappchatlistener.model.Message

@Database(entities = [Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}