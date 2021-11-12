package com.njves.memorandum

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Magnifier
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.njves.memorandum.detail.ARGS_ID

const val TAG = "MemoListFragment"
class MemoListFragment: Fragment(), MemoAdapter.OnClickItemListener {
    private lateinit var edQuery: EditText
    private lateinit var rvMemo: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private val memoListViewModel: MemoListViewModel by viewModels()
    private val adapter: MemoAdapter = MemoAdapter(listOf(), this)
    private val animNavOptions: NavOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.nav_default_enter_anim)
        .setExitAnim(R.anim.nav_default_exit_anim)
        .build()

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
        val callback = SimpleItemTouchHelperCallback(this.adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(rvMemo)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_memoListFragment_to_memoDetailFragment, null, animNavOptions)
        }
        memoListViewModel.memoLiveData.observe(viewLifecycleOwner, {
            Log.d(TAG, it.toString())
            updateUi(it)
        })

    }



    override fun onStart() {
        super.onStart()
        edQuery.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val matches = memoListViewModel.searchMemoBySubject(s.toString())
                updateUi(matches)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateUi(list: List<Memo>) {

        val callback = MemoDiffUtilCallback(adapter.memoList, list)
        val result = DiffUtil.calculateDiff(callback)
        adapter.memoList = list.toList()
        result.dispatchUpdatesTo(adapter)
    }

    override fun onClick(memo: Memo) {
        val bundle = Bundle()
        bundle.putSerializable(ARGS_ID, memo.id)
        findNavController().navigate(R.id.memoDetailFragment,
            bundle,
            animNavOptions)
    }

    override fun onSwipe(memo: Memo) {
        memoListViewModel.removeMemo(memo)
    }

    override fun onStop() {
        super.onStop()
    }
}