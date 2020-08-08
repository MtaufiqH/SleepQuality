package app.taufiq.trackmysleepquality.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created By Taufiq on 8/7/2020.
 *
 */

@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

    // database have to know the DAO for queries
    abstract val SleepDatabaseDao: SleepDao

    /** allow clients to access method for creating
     * or getting database without instantiating class. */
    companion object {
        // database initializer must private nullable variable

        /* The INSTANCE variable will keep a reference to the database, once one has been created.
        This helps you avoid repeatedly opening connections to the database, which is expensive. */

        /*The value of a volatile variable will never be cached,
        and all writes and reads will be done to and from the main memory. */
        @Volatile
        private var INSTANCE: SleepDatabase? = null

        fun getInstance(context: Context): SleepDatabase {
            /**
             * @synchronized means that only one thread of execution at a time can enter this block of code,
             * which makes sure the database
             * only gets initialized once.
             * */
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SleepDatabase::class.java,
                        "sleep_history_database")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}