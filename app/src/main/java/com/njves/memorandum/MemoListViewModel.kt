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


}