package app.taufiq.trackmysleepquality.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.taufiq.trackmysleepquality.db.SleepDao
import java.lang.IllegalArgumentException

/**
 * Created By Taufiq on 8/7/2020.
 *
 */
class SleepTrackerViewModelFactory(
    private val dataSource: SleepDao,
    private val application: Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SleepTrackerViewModel::class.java)){
            return SleepTrackerViewModel(dataSource,application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class!")
    }
}