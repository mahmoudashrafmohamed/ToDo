package com.example.mahmoud_ashraf.todo.data.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * Created by mahmoud_ashraf on 17,04,2019
 *  This is where the database (create, read, update and delete) operations are defined
 *  don't forget annotation :)
 */
@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Query("DELETE FROM notes_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM notes_table ")
    fun getAllNotes(): LiveData<List<Note>> // wrap with LiveData for observing the data changes
}