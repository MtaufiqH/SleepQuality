package app.taufiq.trackmysleepquality.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.taufiq.trackmysleepquality.R
import app.taufiq.trackmysleepquality.db.SleepNight
import app.taufiq.trackmysleepquality.util.TextItemViewHolder

/**
 * Created By Taufiq on 8/12/2020.
 *
 */
class SleepNightAdapter : RecyclerView.Adapter<TextItemViewHolder>() {
    // list of the night
    var data = listOf<SleepNight>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)
         val view = layoutInflate.inflate(R.layout.text_item_layout,parent,false) as TextView
        return TextItemViewHolder(view)
    }

    override fun getItemCount() = data.size

    /** The onBindViewHolder()function is called by RecyclerView
     * to display the data for one list item
     * at the specified position.*/
    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        if (item.sleepQuality <= 1) {
            holder.text.setTextColor(Color.RED)
        } else{
            holder.text.setTextColor(Color.BLACK)
        }
        holder.text.text = item.sleepQuality.toString()
    }
}