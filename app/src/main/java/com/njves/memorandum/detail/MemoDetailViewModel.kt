package com.njves.memorandum.detail

import androidx.lifecycle.*
import com.njves.memorandum.Memo
import com.njves.memorandum.MemoRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class MemoDetailViewModel: ViewModel() {
    private val repository: MemoRepository = MemoRepository.get()
    private val memoIdLiveData = MutableLiveData<UUID>()

    val memoLiveData: LiveData<Memo?> = Transformations.switchMap(memoIdLiveData) { memoId ->
        repository.getMemo(memoId)
    }

    fun loadMemo(memoId: UUID) {
        memoIdLiveData.value = memoId
    }

    fun addMemo(memo: Memo) {
        if(!memo.isEmpty()) {
            repository.addMemo(memo)
        }
    }

    fun updateMemo(memo: Memo) {
        // TODO: change to something else
        GlobalScope.launch {
            repository.updateMemo(memo)
        }
    }

}