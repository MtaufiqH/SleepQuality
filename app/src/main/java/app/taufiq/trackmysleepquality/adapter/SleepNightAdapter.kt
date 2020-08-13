package app.taufiq.trackmysleepquality.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.taufiq.trackmysleepquality.R
import app.taufiq.trackmysleepquality.db.SleepNight
import app.taufiq.trackmysleepquality.util.convertDurationToFormatted
import app.taufiq.trackmysleepquality.util.convertNumericQualityToString

/**
 * Created By Taufiq on 8/12/2020.
 *
 */
class SleepNightAdapter :
    ListAdapter<SleepNight, SleepNightAdapter.viewHolder>(SleepNightDiffCallback()) {
    // list of the night
//    var data = listOf<SleepNight>()

    // the view Holder
    class viewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // get reference of the views
        // views that hold inside this, will be updated

        val sleepLenght = itemView.findViewById<TextView>(R.id.sleep_length)
        val quality = itemView.findViewById<TextView>(R.id.quality_string)
        val qualityImage = itemView.findViewById<ImageView>(R.id.quality_image)

        fun binding(item: SleepNight) {
            val res = itemView.context.resources
            sleepLenght.text =
                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            quality.text = convertNumericQualityToString(item.sleepQuality, res)
            qualityImage.setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )
        }


        companion object {
            fun from(parent: ViewGroup): viewHolder {
                val layoutInflate = LayoutInflater.from(parent.context)
                val view = layoutInflate.inflate(R.layout.list_item_sleep_night, parent, false)
                return viewHolder(view)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder.from(parent)
    }

    /* override fun getItemCount() = data.size*/

    /** The onBindViewHolder()function is called by RecyclerView
     * to display the data for one list item
     * at the specified position.*/
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val item = getItem(position)
        holder.binding(item)
    }


    // difUtil
    class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {

        override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight) =
            oldItem.nightId == newItem.nightId

        override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight) =
            oldItem == newItem

    }


}


