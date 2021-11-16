package com.njves.memorandum.detail

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.njves.memorandum.Memo
import com.njves.memorandum.R
import java.util.*
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.net.Uri
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val TAG = "MemoDetailFragment"
const val ARGS_ID = "id"
const val CREATE_MODE = 0
const val UPDATE_MODE = 1

class MemoDetailFragment : Fragment() {
    private var memo: Memo = Memo()
    private lateinit var edSubject: EditText
    private lateinit var edContent: EditText
    private lateinit var tvDate: TextView
    private lateinit var rvImages: RecyclerView
    private lateinit var btnAddImage: Button
    private var mode: Int = CREATE_MODE
    private val viewModel: MemoDetailViewModel by viewModels()
    private var actionBar: ActionBar? = null
    private var adapter = ImageAdapter(mutableListOf())
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            GlobalScope.launch {
                val bitmap = Picasso.get().load(uri).resize(300, 350).get()
                requireActivity().runOnUiThread {
                    adapter.addItem(bitmap)
                }
            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getSerializable(ARGS_ID)?.let {
            mode = UPDATE_MODE
            memo = viewModel.findMemoById(it as UUID) ?: return
        }
        Log.d(TAG, memo.toString())
        Log.d(TAG, "mode $mode")
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_memo_detail, container, false)
        edSubject = view.findViewById(R.id.ed_subject)
        edContent = view.findViewById(R.id.ed_content)
        tvDate = view.findViewById(R.id.tv_date)
        rvImages = view.findViewById(R.id.rv_images)
        btnAddImage = view.findViewById(R.id.btn_add_image)
        actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        if(memo.subject.isNotEmpty()) {
            actionBar?.title = memo.subject
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edSubject.setText(memo.subject)
        edContent.setText(memo.content)
        tvDate.apply {
            text = memo.getFormatDate()
        }
        rvImages.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@MemoDetailFragment.adapter
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

        tvDate.setOnClickListener {
            val newCalendar = GregorianCalendar()
            val listener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                newCalendar.set(Calendar.YEAR, year)
                newCalendar.set(Calendar.MONTH, monthOfYear)
                newCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                memo.date = newCalendar.time
                tvDate.text = memo.getFormatDate()
            }
            val year = newCalendar.get(Calendar.YEAR)
            val month = newCalendar.get(Calendar.MONTH)
            val day = newCalendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
            datePickerDialog.show()
        }




        btnAddImage.setOnClickListener {
            getContent.launch("image/*")
        }


    }
    private fun saveMemo() {
        when(mode) {
            UPDATE_MODE -> {
                viewModel.updateMemo(memo)
            }
            CREATE_MODE -> {
                viewModel.addMemo(memo)
                mode = UPDATE_MODE
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "OnStop current $memo")
        saveMemo()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_memo_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_add -> {
                saveMemo()
                findNavController().popBackStack()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}