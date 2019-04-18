package com.example.mahmoud_ashraf.todo.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mahmoud_ashraf.todo.data.model.Note

/**
 * Created by mahmoud_ashraf on 17,04,2019
 * This class ties together the Entities + DAOs
 */
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    // this fun is only return NoteDao obj and no need to add body to it
    // so i made it abstract fun ...
    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase? {
            // to make it Singletone ...
            if (instance == null) {
                synchronized(NoteDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java, "note_database"
                    )
                        // if you change in db schema -> this line tell Room to destructively recreate your app's
                        // database tables in cases where a migration path between schema versions is missing
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }

    }

}