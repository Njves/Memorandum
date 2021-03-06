package com.njves.memorandum

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.room.Room


import com.njves.memorandum.database.MemoDatabase
import java.io.File
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MemoRepository(private val context: Context) {

    companion object {
        private var instance: MemoRepository? = null

        fun init(context: Context) {
            if(instance == null) {
                instance = MemoRepository(context)
            }
        }

        fun get(): MemoRepository {
            return instance ?: throw IllegalStateException("MemoRepository must be initialize")
        }
    }

    private val db = Room.databaseBuilder(
        context,
        MemoDatabase::class.java, "memo.db"
    ).build()

    private val memoDao = db.memoDao()
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun getSortedList(): LiveData<List<Memo>> = memoDao.getMemosByDate()

    fun getMemo(id: UUID): LiveData<Memo?> = memoDao.getMemo(id)

    fun addMemo(memo: Memo) {
        executor.execute {
            memoDao.addMemo(memo)
        }
    }

    fun removeMemo(memo: Memo) {
        executor. execute {
            memoDao.removeMemo(memo)
        }
    }

    fun updateMemo(memo: Memo) {
        memoDao.updateMemo(memo)
    }

}