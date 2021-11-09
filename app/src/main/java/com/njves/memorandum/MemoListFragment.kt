package com.njves.memorandum

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

const val TAG = "MemoListFragment"
class MemoListFragment: Fragment(), MemoAdapter.OnClickItemListener {
    private lateinit var edQuery: EditText
    private lateinit var rvMemo: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private val memoListViewModel: MemoListViewModel by viewModels()
    private val adapter: MemoAdapter = MemoAdapter(listOf(), this)

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
            val layoutManager = LinearLayoutManager(this@MemoListFragment.requireContext())
            this.layoutManager = layoutManager
            this.adapter = this@MemoListFragment.adapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabAdd.setOnClickListener {
            // TODO: Удалить
//            val memo = Memo()
//            memo.subject = "${(Math.random() * 66).toInt()}"
//            memo.content = "Мяу"
//            memoListViewModel.addMemo(memo)
            findNavController().navigate(R.id.action_memoListFragment_to_memoDeatilFragment)
            Snackbar.make(it, "Make new memo", Snackbar.LENGTH_SHORT).show()
        }
        memoListViewModel.memoLiveData.observe(viewLifecycleOwner, {
            updateUi(it)
        })
    }

    override fun onStart() {
        super.onStart()
        edQuery.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val l = memoListViewModel.searchMemoBySubject(s.toString())
                updateUi(l)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateUi(list: MutableList<Memo>) {
        val callback = MemoDiffUtilCallback(adapter.memoList, list)
        val result = DiffUtil.calculateDiff(callback)
        adapter.memoList = list.toList()
        result.dispatchUpdatesTo(adapter)
    }

    override fun onClick(memo: Memo) {
        memoListViewModel.removeMemo(memo)
    }

}