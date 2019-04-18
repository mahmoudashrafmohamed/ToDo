package com.example.mahmoud_ashraf.todo.data.db


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mahmoud_ashraf.todo.data.model.Note

/**
 * Created by mahmoud_ashraf on 17,04,2019
 *  This is where the database (create, read, update and delete) operations are defined
 *  don't forget annotation :)
 */
@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM notes_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM notes_table ")
    fun getAllNotes(): LiveData<List<Note>> // wrap with LiveData for observing the data changes
}