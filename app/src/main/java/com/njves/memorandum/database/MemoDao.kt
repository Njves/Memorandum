package com.njves.memorandum.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.njves.memorandum.Memo
import java.util.*

@Dao
interface MemoDao {
    @Query("SELECT * FROM memo")
    fun getAll(): LiveData<List<Memo>>

    @Query("SELECT * FROM memo WHERE id=(:id)")
    fun getMemo(id: UUID): Memo

    @Insert
    fun addMemo(memo: Memo)

    @Delete
    fun removeMemo(memo: Memo)
}