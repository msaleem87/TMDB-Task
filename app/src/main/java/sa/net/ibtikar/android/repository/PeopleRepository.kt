package sa.net.ibtikar.android.repository

import androidx.lifecycle.MutableLiveData
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.request
import sa.net.ibtikar.android.api.service.PeopleService
import sa.net.ibtikar.android.models.entity.Person
import sa.net.ibtikar.android.models.network.PersonDetail
import sa.net.ibtikar.android.room.PeopleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class PeopleRepository constructor(
        private val peopleService: PeopleService,
        private val peopleDao: PeopleDao
) : Repository {

  init {
    Timber.d("Injection PeopleRepository")
  }

  suspend fun loadPeople(page: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Person>>()
    var people = peopleDao.getPeople(page)
    if (people.isEmpty()) {
      peopleService.fetchPopularPeople(page).request { response ->
        response.onSuccess {
          data?.let { data ->
            people = data.results
            people.forEach { it.page = page }
            liveData.postValue(people)
            peopleDao.insertPeople(people)
          }
        }.onError {
          error(message())
        }.onException {
          error(message())
        }
      }
    }
    liveData.apply { postValue(people) }
  }

  suspend fun loadPersonDetail(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<PersonDetail>()
    val person = peopleDao.getPerson(id)
    var personDetail = person.personDetail
    if (personDetail == null) {
      peopleService.fetchPersonDetail(id).request { response ->
        response.onSuccess {
          data?.let { data ->
            personDetail = data
            person.personDetail = personDetail
            liveData.postValue(personDetail)
            peopleDao.updatePerson(person)
          }
        }.onError {
          error(message())
        }.onException {
          error(message())
        }
      }
    }
    liveData.apply { postValue(person.personDetail) }
  }
}
