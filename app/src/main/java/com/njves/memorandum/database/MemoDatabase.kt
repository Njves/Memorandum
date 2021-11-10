package com.njves.memorandum.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.njves.memorandum.Memo

@Database(entities = [Memo::class], version = 1)
@TypeConverters(MemoTypeConverters::class)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao

}
