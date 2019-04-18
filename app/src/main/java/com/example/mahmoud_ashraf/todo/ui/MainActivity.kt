package com.example.mahmoud_ashraf.todo.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mahmoud_ashraf.mygroupeex.NoteItem
import com.example.mahmoud_ashraf.todo.R
import com.example.mahmoud_ashraf.todo.data.model.Note
import com.example.mahmoud_ashraf.todo.viewmodel.NoteViewModel
import com.xwray.groupie.GroupAdapter
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
          //  Toast.makeText(this,"+++",Toast.LENGTH_SHORT).show()
           // noteViewModel.insert(Note("hello","des des des des des des des"))
            startActivityForResult(
                Intent(this, AddNoteActivity::class.java),
                900
            )
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

    /*
    add menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 900 && resultCode == Activity.RESULT_OK) {
            val newNote = Note(
                data!!.getStringExtra("_TITLE"),
                data.getStringExtra("_DESCRIPTION")
            )
            noteViewModel.insert(newNote)

            Toast.makeText(this, "Note Added Successfully :)", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Note not saved!", Toast.LENGTH_SHORT).show()
        }


    }
}
