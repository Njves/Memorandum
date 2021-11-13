package com.njves.memorandum.detail

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.njves.memorandum.Memo
import com.njves.memorandum.R
import java.util.*


private const val TAG = "MemoDetailFragment"
const val ARGS_ID = "id"
const val CREATE_MODE = 0
const val UPDATE_MODE = 1
class MemoDetailFragment : Fragment() {
    private var memo: Memo = Memo()
    private lateinit var edSubject: EditText
    private lateinit var edContent: EditText
    private lateinit var tvDate: TextView
    private var mode: Int = CREATE_MODE
    private val viewModel: MemoDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getSerializable(ARGS_ID)?.let {
            mode = UPDATE_MODE
            memo = viewModel.findMemoById(it as UUID) ?: return
        }
        Log.d(TAG, memo.toString())
        Log.d(TAG, "mode $mode")



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_memo_detail, container, false)
        edSubject = view.findViewById(R.id.ed_subject)
        edContent = view.findViewById(R.id.ed_content)
        tvDate = view.findViewById(R.id.tv_date)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = memo.subject
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edSubject.setText(memo.subject)
        edContent.setText(memo.content)
        tvDate.apply {
            text = memo.getFormatDate()
            setOnClickListener {

            }
        }

    }

    override fun onStart() {
        super.onStart()

        edSubject.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                memo.subject = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        edContent.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                memo.content = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "OnStop current $memo")
        when(mode) {
            UPDATE_MODE -> {
                viewModel.updateMemo(memo)
            }
            CREATE_MODE -> {
                viewModel.addMemo(memo)
            }
        }
    }


}