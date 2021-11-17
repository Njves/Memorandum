package com.njves.memorandum

import android.graphics.Color
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemoAdapter(var memoList: List<Memo>, val onClickListener: OnClickItemListener) :
    RecyclerView.Adapter<MemoAdapter.MemoViewHolder>(),
    ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memo, parent, false)
        return MemoViewHolder(view)

    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(memoList[position])
    }

    override fun getItemCount(): Int = memoList.size

    inner class MemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSubject: TextView = itemView.findViewById(R.id.tv_subject)
        private val tvContent: TextView = itemView.findViewById(R.id.tv_content)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private lateinit var memo: Memo

        fun bind(memo: Memo) {
            this.memo = memo
            tvSubject.text = memo.subject
            tvContent.text = memo.content
            tvDate.text = memo.formatDate
            if(memo.subject.isEmpty()) {
                tvSubject.setTextColor(Color.GRAY)
                tvSubject.text = "Без заголовка"
            }
            if(memo.content.isEmpty()) {
                tvContent.setTextColor(Color.GRAY)
                tvContent.text = "Нет описания"
            }
            itemView.setOnClickListener {
                onClickListener.onClick(memo)
            }
        }
    }

    interface OnClickItemListener {
        fun onClick(memo: Memo)
        fun onSwipe(memo: Memo, position: Int)
    }

    override fun onItemDismiss(position: Int) {
        onClickListener.onSwipe(memoList[position], position)
    }




}