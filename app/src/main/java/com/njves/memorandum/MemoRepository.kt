package com.njves.memorandum

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.njves.memorandum.database.MemoDatabase
import java.util.concurrent.Executors

class MemoRepository(val context: Context) {
    val db = Room.databaseBuilder(
        context,
        MemoDatabase::class.java, "memo.db"
    ).build()
    val executor = Executors.newSingleThreadExecutor()

    fun getList(): LiveData<List<Memo>> = db.memoDao().getAll()


}