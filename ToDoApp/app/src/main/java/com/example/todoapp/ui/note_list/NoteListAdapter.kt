package com.example.todoapp.ui.note_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoapp.R
import com.example.todoapp.data.entities.Note
import com.example.todoapp.databinding.ItemNoteGridBinding
import com.example.todoapp.databinding.ItemNoteListBinding
import java.lang.IllegalArgumentException
import java.util.ArrayList

class NoteListAdapter(
    private val staggeredGridLayoutManager: StaggeredGridLayoutManager,
    private val listener: OnNoteClickListener
): RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder>() {

    private val viewTypeGrid = R.layout.item_note_grid
    private val viewTypeList = R.layout.item_note_list

    private val noteList = arrayListOf<Note>()

    override fun getItemViewType(position: Int): Int {
        return if(staggeredGridLayoutManager.spanCount == spanCountTwo) {
            viewTypeGrid
        } else {
            viewTypeList
        }
    }

    fun setData(data: ArrayList<Note>) {
        val diffCallback = ListsDiffCallback(noteList, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        noteList.clear()
        noteList.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        return when(viewType) {
            R.layout.item_note_grid -> {
                NoteListViewHolder.GridViewHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_note_grid, parent, false),
                    this.listener
                )
            }

            R.layout.item_note_list -> {
                NoteListViewHolder.ListViewHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_note_list, parent, false),
                    this.listener
                )
            }

            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        when(holder) {
            is NoteListViewHolder.GridViewHolder -> {
                holder.bind(noteList[position])
            }

            is NoteListViewHolder.ListViewHolder -> {
                holder.bind(noteList[position])
            }
        }
    }

    override fun getItemCount() = noteList.size

    sealed class NoteListViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
        class GridViewHolder(private val binding: ItemNoteGridBinding, private val listener: OnNoteClickListener): NoteListViewHolder(binding) {
            fun bind(note: Note) {
                binding.note = note
                binding.executePendingBindings()

                binding.root.setOnClickListener {
                    listener.onNoteClicked(note.id)
                }
            }
        }

        class ListViewHolder(private val binding: ItemNoteListBinding, private val listener: OnNoteClickListener): NoteListViewHolder(binding) {
            fun bind(note: Note) {
                binding.note = note
                binding.executePendingBindings()

                binding.root.setOnClickListener {
                    listener.onNoteClicked(note.id)
                }
            }
        }
    }

    inner class ListsDiffCallback(private val oldList: ArrayList<Note>, private val newList: ArrayList<Note>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean = oldList[oldPosition] == newList[newPosition]
    }

    interface OnNoteClickListener {
        fun onNoteClicked(id: Int)
    }

    companion object {
        const val spanCountOne = 1
        const val spanCountTwo = 2
    }
}