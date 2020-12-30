package sa.net.ibtikar.android.utils

import sa.net.ibtikar.android.models.entity.Person
import sa.net.ibtikar.android.models.network.PersonDetail

class MockTestUtil {
  companion object {
    fun mockPerson() = Person(1, mockPersonDetail(), "", false, 123, "", 0f)
    fun mockPersonList(): List<Person> {
      val people = ArrayList<Person>()
      people.add(mockPerson())
      people.add(mockPerson())
      people.add(mockPerson())
      return people
    }
    fun mockPersonDetail(): PersonDetail {
      return PersonDetail("", "", "", emptyList(), "")
    }
  }
}
