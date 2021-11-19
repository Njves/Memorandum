package com.njves.memorandum.database

import android.graphics.Color
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.njves.memorandum.Memo

@Database(entities = [Memo::class], version = 3)
@TypeConverters(MemoTypeConverters::class)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao


}
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE memo add column date date ")
    }
}

val MIGRATION_2_3 = object: Migration(2, 3) {

    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL("ALTER TABLE memo add column color integer not null default ${Color.WHITE}")
    }
}
