package com.njves.memorandum.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import com.njves.memorandum.Memo
import com.njves.memorandum.MemoRepository
import java.util.*

class MemoDetailViewModel: ViewModel() {
    private val repository: MemoRepository = MemoRepository.get()

    fun findMemoById(id: UUID): Memo? {
        return repository.getMemo(id)
    }

    fun addMemo(memo: Memo) {
        if(!memo.isEmpty()) {
            repository.addMemo(memo)
        }
    }
}