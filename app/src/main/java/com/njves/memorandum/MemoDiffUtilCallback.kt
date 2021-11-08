package com.njves.memorandum

import androidx.recyclerview.widget.DiffUtil

class MemoDiffUtilCallback(private val oldList: List<Memo>, private val newList: List<Memo>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].subject == newList[newItemPosition].subject
                && oldList[oldItemPosition].content == newList[newItemPosition].content
    }

}