package app.taufiq.trackmysleepquality.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.taufiq.trackmysleepquality.db.SleepDao
import app.taufiq.trackmysleepquality.db.SleepNight
import kotlinx.coroutines.Job

/**
 * Created By Taufiq on 8/14/2020.
 *
 */
class SleepDetailViewModel(private val sleepNightKey: Long = 0L, dataSource: SleepDao) :
    ViewModel() {

    /*
    * Hold a reference to SleepDatabase via SleepDao
    * */
    val database = dataSource

    /*
    * Coroutine setup variables
    * */

    /* ViewModel allows us to cancel all coroutine started by this viewModel*/
    private val viewModelJob = Job()

    private val night: LiveData<SleepNight>
    fun getNight() = night


    init {
        night = database.getNightWithId(sleepNightKey)
    }


    /**
     * Variable that tells the fragment whether it should navigate to [SleepTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */

    private val navigateToSleepTracker = MutableLiveData<Boolean?>()

    // when true immediately back to Sleep Tracker Fragment
    val _navigateToSleepTracker: LiveData<Boolean?>
        get() = navigateToSleepTracker


    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    /**
     * Call this immediately after navigating to [SleepTrackerFragment]
     */
    fun doneNavigating() {
        navigateToSleepTracker.value = null
    }

    fun onClose() {
        navigateToSleepTracker.value = false
    }

}