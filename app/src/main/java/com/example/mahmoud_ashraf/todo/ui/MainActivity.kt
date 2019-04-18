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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mahmoud_ashraf.mygroupeex.NoteItem
import com.example.mahmoud_ashraf.todo.R
import com.example.mahmoud_ashraf.todo.data.model.Note
import com.example.mahmoud_ashraf.todo.utils.SwipeTouchCallback
import com.example.mahmoud_ashraf.todo.viewmodel.NoteViewModel
import com.sdsmdg.tastytoast.TastyToast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    val groupAdapter = GroupAdapter<ViewHolder>()
    var notes_list : List<Note> ?= null

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
        // to enable swipe ...
        ItemTouchHelper(touchCallback).attachToRecyclerView(recycler_view)

        noteViewModel.getAllNotes().observe(this,
            Observer<List<Note>> {
                    t ->
                TastyToast.makeText(this,  "Data Updated!", TastyToast.LENGTH_SHORT,
                    TastyToast.INFO).show()
                notes_list = t
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
              //  Toast.makeText(this, "All notes deleted!", Toast.LENGTH_SHORT).show()
                TastyToast.makeText(this,  "All notes deleted!", TastyToast.LENGTH_SHORT,
                    TastyToast.WARNING).show()
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

           // Toast.makeText(this, "Note Added Successfully :)", Toast.LENGTH_SHORT).show()
            TastyToast.makeText(this, "Note Added Successfully!", TastyToast.LENGTH_SHORT,
                TastyToast.SUCCESS).show()
        } else {
           // Toast.makeText(this, "Note not saved!", Toast.LENGTH_SHORT).show()
        }


    }

    // by lazy ->
    // Your variable will not be initialized unless you use that variable in your code.
    // It will be initialized only once after that we always use the same value.
    private val touchCallback: SwipeTouchCallback by lazy {
        object : SwipeTouchCallback() {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               // Delete from db ...
                 noteViewModel.deleteNotes(notes_list!!.get(viewHolder.adapterPosition))

            }
        }
    }
}
