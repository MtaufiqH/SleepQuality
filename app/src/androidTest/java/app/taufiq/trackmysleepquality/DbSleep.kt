package app.taufiq.trackmysleepquality

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.taufiq.trackmysleepquality.db.SleepDao
import app.taufiq.trackmysleepquality.db.SleepDatabase
import app.taufiq.trackmysleepquality.db.SleepNight
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

/**
 * Created By Taufiq on 8/8/2020.
 *
 */

@RunWith(AndroidJUnit4::class)
class DbSleep {

    private lateinit var databaseDao: SleepDao
    private lateinit var db: SleepDatabase


    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context,SleepDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()

        databaseDao = db.SleepDatabaseDao

    }


    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertAndGetNight(){
        val night = SleepNight()
        databaseDao.insert(night)
        val tonight = databaseDao.getTonight()
        assertEquals(tonight?.sleepQuality,-1)

    }


}