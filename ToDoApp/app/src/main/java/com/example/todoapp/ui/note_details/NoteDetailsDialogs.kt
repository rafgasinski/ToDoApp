package com.example.todoapp.ui.note_details

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.todoapp.R
import com.example.todoapp.databinding.DialogDeleteNoteBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate

class DeleteNoteDialog: BottomSheetDialogFragment() {

    private var _binding: DialogDeleteNoteBinding? = null
    private val binding get() = _binding!!

    var onDeleteNote: (() -> Unit)? = null

    override fun onResume() {
        super.onResume()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_delete_note, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancel.setOnClickListener {
            dialog?.dismiss()
        }

        binding.delete.setOnClickListener {
            onDeleteNote?.invoke()
            dialog?.dismiss()
        }
    }
}

class DatePickerFragment: DialogFragment() {
    private lateinit var localDate: LocalDate
    var onDateSet: ((date: LocalDate) -> Unit)? = null

    companion object {
        private const val SUPPLIED_DATE = "date"

        fun newInstance(date: LocalDate? = null): DatePickerFragment {
            val dialog = DatePickerFragment()
            val args = Bundle().apply {
                putSerializable(SUPPLIED_DATE, date)
            }
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        localDate = arguments?.getSerializable(SUPPLIED_DATE) as LocalDate? ?: LocalDate.now()

        val localDateYear = localDate.year
        val localDateMonth = localDate.monthValue - 1
        val localDateDay = localDate.dayOfMonth

        val listener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            onDateSet?.invoke(LocalDate.of(year, monthOfYear + 1, dayOfMonth))
        }

        return DatePickerDialog(requireActivity(), listener, localDateYear, localDateMonth, localDateDay)
    }
}