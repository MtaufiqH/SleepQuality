package app.taufiq.trackmysleepquality.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import app.taufiq.trackmysleepquality.db.SleepDao

/**
 * Created By Taufiq on 8/7/2020.
 *
 */
class SleepTrackerViewModel(val database: SleepDao, application: Application): AndroidViewModel(application) {
}