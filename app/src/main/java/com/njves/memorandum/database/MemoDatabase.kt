package com.njves.memorandum.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.njves.memorandum.Memo

@Database(entities = [Memo::class], version = 2)
@TypeConverters(MemoTypeConverters::class)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao


}
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE memo add column date date ")
    }
}
