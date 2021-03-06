package com.njves.memorandum.adapter

import androidx.recyclerview.widget.DiffUtil
import com.njves.memorandum.Memo

class MemoDiffUtilCallback(private val oldList: List<Memo>, private val newList: List<Memo>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].subject == newList[newItemPosition].subject
                && oldList[oldItemPosition].content == newList[newItemPosition].content
                && oldList[oldItemPosition].date == newList[newItemPosition].date
                && oldList[oldItemPosition].color == newList[newItemPosition].color
                && oldList[oldItemPosition].completed == newList[newItemPosition].completed
    }

}