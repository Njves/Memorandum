package com.njves.memorandum

import android.content.Context
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class MemoAdapter(var memoList: List<Memo>, val onClickListener: OnClickItemListener) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.memo_item, parent, false)
        return MemoViewHolder(view)

    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(memoList[position])
    }

    override fun getItemCount(): Int = memoList.size

    inner class MemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSubject: TextView = itemView.findViewById(R.id.tv_subject)
        private val tvContent: TextView = itemView.findViewById(R.id.tv_content)
        private lateinit var memo: Memo


        fun bind(memo: Memo) {
            this.memo = memo
            tvSubject.text = memo.subject
            tvContent.text = memo.content
            itemView.setOnClickListener {
                onClickListener.onClick(memo)
            }

        }
    }

    interface OnClickItemListener {
        fun onClick(memo: Memo)
    }


}