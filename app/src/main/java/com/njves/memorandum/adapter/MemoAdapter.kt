package com.njves.memorandum.adapter

import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.njves.memorandum.Memo
import com.njves.memorandum.R

class MemoAdapter(private val context: Context, var memoList: List<Memo>, val onClickListener: OnClickItemListener) :
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
        private val root: MaterialCardView = itemView.findViewById(R.id.root)
        private val tvSubject: TextView = itemView.findViewById(R.id.tv_subject)
        private val tvContent: TextView = itemView.findViewById(R.id.tv_content)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private lateinit var memo: Memo

        fun bind(memo: Memo) {
            this.memo = memo
            tvSubject.text = memo.subject
            tvContent.text = memo.content
            tvDate.text = memo.formatDate
            root.setCardBackgroundColor(memo.color)
            if(memo.subject.isEmpty()) {
                tvSubject.setTextColor(Color.GRAY)
                tvSubject.text = context.getString(R.string.message_subject_empty)
            }
            if(memo.content.isEmpty()) {
                tvContent.setTextColor(Color.GRAY)
                tvContent.text = context.getString(R.string.message_content_empty)
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