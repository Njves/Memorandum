package com.njves.memorandum.database

import androidx.room.Dao
import androidx.room.Query
import com.njves.memorandum.Memo

@Dao
interface MemoDao {
    @Query("SELECT * FROM memo")
    fun getAll(): List<Memo>
}