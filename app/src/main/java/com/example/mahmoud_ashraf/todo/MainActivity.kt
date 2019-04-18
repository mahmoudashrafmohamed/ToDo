package com.example.mahmoud_ashraf.todo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mahmoud_ashraf.mygroupeex.NoteItem
import com.example.mahmoud_ashraf.todo.data.model.Note
import com.example.mahmoud_ashraf.todo.viewmodel.NoteViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.R.attr.layoutManager
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    val groupAdapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)


        fab.setOnClickListener {
            Toast.makeText(this,"+++",Toast.LENGTH_SHORT).show()
            noteViewModel.insert(Note("hello","des des des des des des des"))
        }

        recycler_view.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)

        }

        noteViewModel.getAllNotes().observe(this,
            Observer<List<Note>> {
                    t ->
                groupAdapter.clear()
                groupAdapter.addAll(t.toNoteItem()!!)
            })
    }

    /**
     * Returns a list containing the results of applying the given [transform] function
     * to each element in the original collection.
     */
    private fun List<Note>.toNoteItem() : List<NoteItem> {
        return this.map {
            NoteItem(it)
        }
    }
}
