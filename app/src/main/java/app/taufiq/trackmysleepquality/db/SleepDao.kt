package app.taufiq.trackmysleepquality.db

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Created By Taufiq on 8/7/2020.
 *
 */
interface SleepDao {
    @Insert
    fun insert(night: SleepNight)

    @Update
    fun update(night: SleepNight)

    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId= :key")
    fun get(key: Long): SleepNight?

    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): SleepNight?

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>

    /*
    * Select and returns the night with given nightId
    * */
    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId= :key")
    fun getNightWithId(key: Long): LiveData<SleepNight>
}