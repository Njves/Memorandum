package com.njves.memorandum

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.njves.memorandum.database.MemoDatabase
import java.lang.IllegalStateException
import java.util.concurrent.Executors

class MemoRepository(val context: Context) {

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
    val executor = Executors.newSingleThreadExecutor()

    fun getList(): LiveData<List<Memo>> = memoDao.getAll()

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

}