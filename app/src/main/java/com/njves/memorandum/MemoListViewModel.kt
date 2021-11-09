package com.njves.memorandum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MemoListViewModel : ViewModel() {
    val memoLiveData: MutableLiveData<MutableList<Memo>> by lazy {
        MutableLiveData<MutableList<Memo>>()
    }

    fun addMemo(memo: Memo) {
        val list =  memoLiveData.value ?: mutableListOf()
        list.add(memo)
        memoLiveData.value = list
    }

    fun removeMemo(memo: Memo) {
        val list =  memoLiveData.value ?: mutableListOf()
        list.remove(memo)
        memoLiveData.value = list
    }

    fun searchMemoBySubject(query: String): MutableList<Memo> {
        val memos = memoLiveData.value ?: listOf()
        val match = mutableListOf<Memo>()
        memos.forEach {
            if(it.subject.startsWith(query, true)) {
                match.add(it)
            }
        }
        return match
    }
}