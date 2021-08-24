package com.example.todoapp.ui.note_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoapp.R
import com.example.todoapp.data.base.Resource
import com.example.todoapp.data.entities.Note
import com.example.todoapp.databinding.FragmentNoteListBinding
import com.example.todoapp.utils.PreferencesManager
import com.example.todoapp.utils.gone
import com.example.todoapp.utils.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class NoteListFragment: Fragment(), NoteListAdapter.OnNoteClickListener {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private val preferencesManager: PreferencesManager by inject()
    private val noteListViewModel: NoteListViewModel by viewModel()

    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager

    private val noteListAdapter by lazy {
        NoteListAdapter(staggeredGridLayoutManager, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.switch_layout -> {
                    switchLayout()
                    true
                }

                else -> false
            }
        }

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(NoteListFragmentDirections.actionNoteListFragmentToNoteDetailsFragment(noteId = -1, isNewNote = true))
        }

        staggeredGridLayoutManager = if(preferencesManager.useGridLayout) {
            binding.toolbar.menu.getItem(0).icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_list)
            StaggeredGridLayoutManager(NoteListAdapter.spanCountTwo, RecyclerView.VERTICAL)
        } else {
            binding.toolbar.menu.getItem(0).icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_grid)
            StaggeredGridLayoutManager(NoteListAdapter.spanCountOne, RecyclerView.VERTICAL)
        }

        binding.noteListRecyclerView.apply {
            adapter = noteListAdapter
            layoutManager = staggeredGridLayoutManager
        }

        noteListViewModel.notesSortedByCreationDate.asLiveData().observe(viewLifecycleOwner, {
            when(it) {
                is Resource.Loading -> {
                    with(binding) {
                        progressBar.visible()
                        emptyNoteListInfo.gone()
                        noteListRecyclerView.gone()
                    }
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    it.data
                        ?.takeIf { list -> list.isNotEmpty() }
                        ?.let { noteList ->
                            binding.emptyNoteListInfo.gone()
                            binding.noteListRecyclerView.visible()
                            noteListAdapter.setData(noteList as ArrayList<Note>)
                        } ?: kotlin.run {
                        binding.noteListRecyclerView.gone()
                        binding.emptyNoteListInfo.visible()
                    }
                }

                is Resource.Error -> {
                    it.message?.let { event ->
                        event.getContentIfNotHandledOrReturnNull()?.let { message ->
                            binding.progressBar.gone()
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }

    private fun switchLayout() {
        if(staggeredGridLayoutManager.spanCount == NoteListAdapter.spanCountTwo) {
            staggeredGridLayoutManager.spanCount = NoteListAdapter.spanCountOne
            binding.toolbar.menu.getItem(0).icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_grid)
            preferencesManager.useGridLayout = false
        } else {
            staggeredGridLayoutManager.spanCount = NoteListAdapter.spanCountTwo
            binding.toolbar.menu.getItem(0).icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_list)
            preferencesManager.useGridLayout = true
        }

        noteListAdapter.notifyItemChanged(0, noteListAdapter.itemCount)
    }

    override fun onNoteClicked(id: Int) {
        findNavController().navigate(NoteListFragmentDirections.actionNoteListFragmentToNoteDetailsFragment(noteId = id, isNewNote = false))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}