package app.taufiq.trackmysleepquality.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.taufiq.trackmysleepquality.db.SleepDao

/**
 * Created By Taufiq on 8/7/2020.
 *
 */
class SleepQualityViewModelFactory(
    private val sleepNightKey: Long,
    private val dataSource: SleepDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java)) {
            return SleepQualityViewModel(sleepNightKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}