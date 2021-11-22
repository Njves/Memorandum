package com.njves.memorandum.database

import android.graphics.Color
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.njves.memorandum.Memo

@Database(entities = [Memo::class], version = 4)
@TypeConverters(MemoTypeConverters::class)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao


}
