package sa.net.ibtikar.android.room

import androidx.room.Database
import androidx.room.RoomDatabase
import sa.net.ibtikar.android.models.entity.Person

@Database(entities = [(Person::class)],
  version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
  abstract fun peopleDao(): PeopleDao
}
