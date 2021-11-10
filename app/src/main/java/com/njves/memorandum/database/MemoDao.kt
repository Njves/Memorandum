package com.njves.memorandum.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.njves.memorandum.Memo

@Dao
interface MemoDao {
    @Query("SELECT * FROM memo")
    fun getAll(): LiveData<List<Memo>>

    @Insert
    fun addMemo(memo: Memo)

    @Delete
    fun removeMemo(memo: Memo)
}