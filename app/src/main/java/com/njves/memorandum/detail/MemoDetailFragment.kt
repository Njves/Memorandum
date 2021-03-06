package com.njves.memorandum.detail

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.njves.memorandum.Memo
import com.njves.memorandum.R
import java.util.*
import com.jaredrummler.android.colorpicker.ColorShape

import com.jaredrummler.android.colorpicker.ColorPickerDialog

private const val TAG = "MemoDetailFragment"
const val ARGS_ID = "id"
const val CREATE_MODE = 0
const val UPDATE_MODE = 1
private const val COLOR_PICKER_ID = 1

class MemoDetailFragment : Fragment(), ColorPickerDialogListener {
    private var memo: Memo = Memo()
    private lateinit var edSubject: EditText
    private lateinit var edContent: EditText
    private lateinit var tvDate: TextView
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var ivMark: ImageView

    private var mode: Int = CREATE_MODE
    private val viewModel: MemoDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getSerializable(ARGS_ID)?.let {
            mode = UPDATE_MODE
            viewModel.loadMemo(it as UUID)
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
        bottomNavView = view.findViewById(R.id.bottom_nav)
        ivMark = view.findViewById(R.id.iv_mark)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getSerializable(ARGS_ID)?.let {
            viewModel.loadMemo(it as UUID)
        }
        viewModel.memoLiveData.observe(viewLifecycleOwner, { memo ->
            memo?.let {
                this.memo = it
                updateUi()
            }
        })

        bottomNavView.apply {
            this.menu.setGroupCheckable(0, false, true)
        }

    }

    private fun updateUi() {
        edSubject.setText(memo.subject)
        edContent.setText(memo.content)
        tvDate.apply {
            text = memo.formatDate
        }
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_view_mark, requireActivity().theme)
        drawable?.setTint(memo.color)
        ivMark.setImageDrawable(drawable)
        updateToolbar()
    }
    override fun onStart() {
        super.onStart()
        // ???????????? ?????????????? ???????????????????? checkable
        bottomNavView.menu.findItem(R.id.action_mark_completed).isCheckable = memo.completed
        // ?????????????????? ?????????????? ?????????? ???? ?????? ?? ????????????
        bottomNavView.menu.findItem(R.id.action_mark_completed).isChecked = memo.completed
        bottomNavView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.action_color_pick -> {
                    createColorPickerDialog(COLOR_PICKER_ID)

                }
                R.id.action_mark_completed -> {
                    memo.completed = !it.isCheckable
                    it.isCheckable = memo.completed
                }
            }
            return@setOnItemSelectedListener true
        }

        edSubject.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                memo.subject = s.toString()
                memo.completed = true
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
                tvDate.text = memo.formatDate
            }
            val year = newCalendar.get(Calendar.YEAR)
            val month = newCalendar.get(Calendar.MONTH)
            val day = newCalendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
            datePickerDialog.show()
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

    private fun updateToolbar() {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        if(memo.subject.isNotEmpty()) {
            actionBar?.title = memo.subject
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

    override fun onColorSelected(dialogId: Int, color: Int) {
        when(dialogId) {
            COLOR_PICKER_ID -> {
                memo.color = color
                Log.d(TAG, color.toString())
                updateUi()
            }
        }
    }

    override fun onDialogDismissed(dialogId: Int) {

    }

    private fun createColorPickerDialog(id: Int) {
        val dialog = ColorPickerDialog.newBuilder()
            .setColor(Color.RED)
            .setDialogType(ColorPickerDialog.TYPE_PRESETS)
            .setAllowCustom(true)
            .setAllowPresets(true)
            .setColorShape(ColorShape.SQUARE)
            .setDialogId(id)
            .create()
        dialog.setColorPickerDialogListener(this)
        dialog.show(childFragmentManager, "")
    }
}