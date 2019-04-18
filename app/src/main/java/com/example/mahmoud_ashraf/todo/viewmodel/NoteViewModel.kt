package com.example.mahmoud_ashraf.todo.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.mahmoud_ashraf.todo.data.db.Note
import com.example.mahmoud_ashraf.todo.data.db.repository.NoteRepository

/**
 * Created by mahmoud_ashraf on 17,04,2019
 * this class saves data and never destroy when configuration change ...
 */

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: NoteRepository = NoteRepository(application)
    private var allNotes: LiveData<List<Note>> = repository.getAllNotes()

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }


}