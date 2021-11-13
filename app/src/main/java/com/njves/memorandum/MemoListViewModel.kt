package com.njves.memorandum

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MemoListViewModel : ViewModel() {
    private val repository: MemoRepository = MemoRepository.get()

    val memoLiveData: LiveData<List<Memo>> = repository.getList()

    fun removeMemo(memo: Memo) {
        repository.removeMemo(memo)
    }

    //  Функция поиска элементов списка по стартовому суффиксу
    fun searchMemoBySubject(query: String): MutableList<Memo> {
        val memos = memoLiveData.value ?: listOf()
        val match = mutableListOf<Memo>()
        memos.forEach {
            if(it.subject.startsWith(query, true))
                match.add(it)
        }
        return match
    }
}