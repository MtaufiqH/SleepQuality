package app.taufiq.trackmysleepquality.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.taufiq.trackmysleepquality.db.SleepDao

/**
 * Created By Taufiq on 8/14/2020.
 *
 */
class SleepDetailViewModelFactory(
    private val sleepNightKey: Long,
    private val dataSource: SleepDao
) : ViewModelProvider.Factory {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepDetailViewModel::class.java)) {
            return SleepDetailViewModel(sleepNightKey, dataSource) as T
        }
        throw IllegalArgumentException("Uknown ViewModel Class")
    }


}