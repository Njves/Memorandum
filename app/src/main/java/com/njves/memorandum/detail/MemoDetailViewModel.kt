package com.njves.memorandum.detail

import androidx.lifecycle.ViewModel
import com.njves.memorandum.Memo
import com.njves.memorandum.MemoRepository

class MemoDetailViewModel: ViewModel() {
    private val repository: MemoRepository = MemoRepository.get()

    fun addMemo(memo: Memo) {
        repository.addMemo(memo)
    }
}