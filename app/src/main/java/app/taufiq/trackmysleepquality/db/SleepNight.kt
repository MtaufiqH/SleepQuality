package app.taufiq.trackmysleepquality.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created By Taufiq on 8/7/2020.
 *
 */

@Entity(tableName = "daily_sleep_quality_table")
data class SleepNight(

    @PrimaryKey(autoGenerate = true)
    var nightId: Long = 0L,

    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: Long = startTimeMilli,

    @ColumnInfo(name = "sleep_quality")
    var sleepQuality: Int = -1
)