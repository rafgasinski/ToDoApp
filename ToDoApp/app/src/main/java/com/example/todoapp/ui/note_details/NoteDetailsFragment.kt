package com.example.todoapp.ui.note_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.data.entities.Category
import com.example.todoapp.data.entities.Note
import com.example.todoapp.data.entities.NoteUpdate
import com.example.todoapp.databinding.FragmentNoteDetailsBinding
import com.example.todoapp.utils.hideKeyboard
import com.example.todoapp.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class NoteDetailsFragment: Fragment() {

    private var _binding: FragmentNoteDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: NoteDetailsFragmentArgs by navArgs()
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

    private val noteDetailsViewModel: NoteDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(args.isNewNote) {
            binding.detailsNoteText.requestFocus()
        } else {
            noteDetailsViewModel.getNote(args.noteId)
        }

        noteDetailsViewModel.note.observe(viewLifecycleOwner, {
            binding.note = it
        })

        binding.toolbar.apply {
            setNavigationOnClickListener {
                handleNoteDatabase()
            }
            setOnMenuItemClickListener {
                val fm = requireActivity().supportFragmentManager
                when(it.itemId) {
                    R.id.delete_note -> {
                        it.isEnabled = false
                        val dialog = DeleteNoteDialog()
                        dialog.onDeleteNote = {
                            if(!args.isNewNote) {
                                noteDetailsViewModel.deleteNote(args.noteId)
                                requireContext().toast(resources.getString(R.string.deleted_successfully))
                                findNavController().navigateUp()
                            }
                        }
                        dialog.show(fm, null)
                        view.postDelayed({ it.isEnabled = true }, 500)
                    }

                    R.id.edit_due_date -> {
                        it.isEnabled = false
                        with(binding.detailsNoteDueDate) {
                            val dialog = DatePickerFragment.newInstance(date = LocalDate.parse(this.text, dateTimeFormatter))
                            dialog.onDateSet = { date ->
                                this.text = date.format(dateTimeFormatter)
                            }
                            dialog.show(requireActivity().supportFragmentManager, null)
                        }

                        view.postDelayed({ it.isEnabled = true }, 500)
                    }

                    else -> false
                }
            }
        }

        binding.detailsNoteCategory.apply {
            setOnClickListener {
                PopupMenu(this.context, this).run {
                    menuInflater.inflate(R.menu.menu_categories, menu)
                    setOnMenuItemClickListener { item ->
                        this@apply.text = item.title
                        true
                    }
                    show()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleNoteDatabase()
            }
        })

    }

    private fun noteTitleAndTextAreEmpty(): Boolean {
        return binding.detailsNoteTitle.text.isEmpty() && binding.detailsNoteText.text.isEmpty()
    }

    private fun handleNoteDatabase() {
        when {
            args.isNewNote && !noteTitleAndTextAreEmpty() -> {
                noteDetailsViewModel.addNote(Note(
                    title = binding.detailsNoteTitle.text.toString(),
                    text = binding.detailsNoteText.text.toString(),
                    category = Category.valueOf(binding.detailsNoteCategory.text.toString()),
                    dueDate = LocalDate.parse(binding.detailsNoteDueDate.text, dateTimeFormatter),
                    creationDate = LocalDate.now()
                ))
                requireContext().toast(resources.getString(R.string.added_successfully))
            }

            else -> {
                if(noteTitleAndTextAreEmpty()) {
                    noteDetailsViewModel.deleteNote(args.noteId)
                } else {
                    noteDetailsViewModel.updateNote(NoteUpdate(
                        id = args.noteId,
                        title = binding.detailsNoteTitle.text.toString(),
                        text = binding.detailsNoteText.text.toString(),
                        category = Category.valueOf(binding.detailsNoteCategory.text.toString()),
                        dueDate = LocalDate.parse(binding.detailsNoteDueDate.text, dateTimeFormatter),
                    ))
                }
            }
        }
        view?.hideKeyboard()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}