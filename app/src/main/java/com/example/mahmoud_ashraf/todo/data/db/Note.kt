package com.example.mahmoud_ashraf.todo.data.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by mahmoud_ashraf on 17,04,2019
 *  is just a POJO which is also going to be the table in the database
 */
@Entity(tableName = "notes_table")
data class Note (
    var title: String,
    var description: String
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}