package com.njves.memorandum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MemoListFragment: Fragment() {
    private lateinit var edQuery: EditText
    private lateinit var rvMemo: RecyclerView
    private lateinit var fabAdd: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.memo_list_fragment, container, false)
        edQuery = view.findViewById(R.id.ed_query)
        rvMemo = view.findViewById(R.id.rv_memo)
        fabAdd = view.findViewById(R.id.fab_add)
        rvMemo.apply {
            this.layoutManager = LinearLayoutManager(this@MemoListFragment.requireContext())
            this.adapter = MemoAdapter(createList())
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabAdd.setOnClickListener {
            Snackbar.make(it, "Make new memo", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun createList(): List<Memo> {
        val list = mutableListOf<Memo>()
        for(i in 1..10) {
            list.add(Memo("1$i", "2$i"))
        }
        return list.toList()
    }
}