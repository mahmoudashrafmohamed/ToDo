package com.example.mahmoud_ashraf.mygroupeex

import androidx.recyclerview.widget.ItemTouchHelper
import com.example.mahmoud_ashraf.todo.R
import com.example.mahmoud_ashraf.todo.data.model.Note
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.note_item.view.*

/**
 * Created by mahmoud_ashraf on 18,04,2019
 */
/**
 * class extend from Item to represent each row in Groupie recycler and take the model class obj in constructor
 */
class NoteItem(val note: Note): Item<ViewHolder>() {
    // in kotlin you don't need to override viewHolder (findViewById)
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text_view_title.text = note.title
        viewHolder.itemView.text_view_description.text = note.description

    }

    override fun getLayout(): Int {
        return R.layout.note_item
    }

    // todo swipe to delete :)
    // set the direction of swipe you want ....
    override fun getSwipeDirs(): Int {
        return ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    }
}