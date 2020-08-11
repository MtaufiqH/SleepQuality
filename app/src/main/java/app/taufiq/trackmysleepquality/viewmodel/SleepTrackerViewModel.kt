package app.taufiq.trackmysleepquality.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import app.taufiq.trackmysleepquality.db.SleepDao
import app.taufiq.trackmysleepquality.db.SleepNight
import app.taufiq.trackmysleepquality.util.formatNights
import kotlinx.coroutines.*

/**
 * Created By Taufiq on 8/7/2020.
 *
 */
class SleepTrackerViewModel(val database: SleepDao, application: Application) :
    AndroidViewModel(application) {

    private lateinit var viewModelJob: Job

    /**
     * Using Dispatchers.Main means that coroutines launched in the uiScope will run on the main thread.
     * */
    // scope with dispatcher
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val nights = database.getAllNights()

    val nightString = Transformations.map(nights) {
        formatNights(it, application.resources)
    }

    private var tonight = MutableLiveData<SleepNight?>()

    // navigation
    private val navigateToSleepQuality = MutableLiveData<SleepNight>()
    val _navigateToSleepQuality: LiveData<SleepNight>
        get() = navigateToSleepQuality

    // function to triggered button start
    fun doneNavigating() {
        navigateToSleepQuality.value = null
    }


    init {
        initializeTonight()
    }

    // create a coroutines with launch
    private fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }

            night
        }
    }

    // start Tracking
    fun onStartTracking() {
        uiScope.launch {
            val newNight = SleepNight()
            insert(newNight)

            tonight.value = getTonightFromDatabase()
        }
    }

    // stop tracking
    fun onStopTracking() {
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
            navigateToSleepQuality.value = oldNight
        }
    }


    // clear tracking
    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}