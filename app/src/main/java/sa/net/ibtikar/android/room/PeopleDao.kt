package sa.net.ibtikar.android.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import sa.net.ibtikar.android.models.entity.Person

@Dao
interface PeopleDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertPeople(people: List<Person>)

  @Update
  fun updatePerson(person: Person)

  @Query("SELECT * FROM people WHERE id = :id_")
  fun getPerson(id_: Int): Person

  @Query("SELECT * FROM People WHERE page = :page_")
  fun getPeople(page_: Int): List<Person>
}
