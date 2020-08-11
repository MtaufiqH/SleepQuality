package app.taufiq.trackmysleepquality.viewmodel

import android.provider.Contacts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.taufiq.trackmysleepquality.db.SleepDao
import kotlinx.coroutines.*

/**
 * Created By Taufiq on 8/7/2020.
 *
 */
class SleepQualityViewModel(
    private val sleepNightKey: Long = 0L,
    val database: SleepDao
) : ViewModel() {

    // define job and scope
    private val viewModeJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModeJob)



    /* navigate back to @SleepTrackerViewModel */
    private val navigateToSleepTracker = MutableLiveData<Boolean?>()

    val _navigateToSleepTracker: LiveData<Boolean?>
        get()= navigateToSleepTracker

    fun doneNavigating(){
        navigateToSleepTracker.value = null
    }

    // launch coroutine
    fun onSetSleepQuality(quality: Int){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val tonight = database.get(sleepNightKey) ?: return@withContext
                tonight.sleepQuality = quality
                database.update(tonight)
            }

            navigateToSleepTracker.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }

}