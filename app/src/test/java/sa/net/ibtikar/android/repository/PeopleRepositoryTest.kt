package sa.net.ibtikar.android.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.request
import sa.net.ibtikar.android.MainCoroutinesRule
import sa.net.ibtikar.android.api.ApiUtil.getCall
import sa.net.ibtikar.android.api.service.PeopleService
import sa.net.ibtikar.android.models.entity.Person
import sa.net.ibtikar.android.models.network.PeopleResponse
import sa.net.ibtikar.android.models.network.PersonDetail
import sa.net.ibtikar.android.room.PeopleDao
import sa.net.ibtikar.android.utils.MockTestUtil.Companion.mockPerson
import sa.net.ibtikar.android.utils.MockTestUtil.Companion.mockPersonDetail
import sa.net.ibtikar.android.utils.MockTestUtil.Companion.mockPersonList
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalStdlibApi
class PeopleRepositoryTest {

  private lateinit var repository: PeopleRepository
  private val service = mock<PeopleService>()
  private val peopleDao = mock<PeopleDao>()

  @ExperimentalCoroutinesApi
  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    repository = PeopleRepository(service, peopleDao)
  }

  @Test
  fun loadPeopleListFromNetworkTest() = runBlocking {
    val mockResponse = PeopleResponse(1, emptyList(), 0, 0)
    whenever(service.fetchPopularPeople(1)).thenReturn(getCall(mockResponse))
    whenever(peopleDao.getPeople(1)).thenReturn(emptyList())

    val data = repository.loadPeople(1) { }
    val observer = mock<Observer<List<Person>>>()
    data.observeForever(observer)
    verify(peopleDao).getPeople(1)

    val loadFromDB = peopleDao.getPeople(1)
    data.postValue(loadFromDB)
    verify(observer, times(2)).onChanged(loadFromDB)

    val updatedData = mockPersonList()
    whenever(peopleDao.getPeople(1)).thenReturn(updatedData)
    data.postValue(updatedData)
    verify(observer).onChanged(updatedData)

    service.fetchPopularPeople(1).request {
      when (it) {
        is ApiResponse.Success -> {
          TestCase.assertEquals(it.data, CoreMatchers.`is`(mockResponse))
          TestCase.assertEquals(it.data?.results, CoreMatchers.`is`(updatedData))
        }
        else -> MatcherAssert.assertThat(it, CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }

  @Test
  fun loadPersonDetailFromNetworkTest() = runBlocking {
    val mockResponse = mockPersonDetail()
    whenever(service.fetchPersonDetail(1)).thenReturn(getCall(mockResponse))
    whenever(peopleDao.getPerson(1)).thenReturn(mockPerson())

    val data = repository.loadPersonDetail(1) { }
    val observer = mock<Observer<PersonDetail>>()
    data.observeForever(observer)
    verify(peopleDao).getPerson(1)

    val loadFromDB = peopleDao.getPerson(1)
    data.postValue(loadFromDB.personDetail)
    verify(observer, times(2)).onChanged(loadFromDB.personDetail)

    val updatedData = mockPerson()
    whenever(peopleDao.getPerson(1)).thenReturn(updatedData)
    data.postValue(updatedData.personDetail)
    verify(observer, times(3)).onChanged(updatedData.personDetail)

    service.fetchPersonDetail(1).request {
      when (it) {
        is ApiResponse.Success -> {
          TestCase.assertEquals(it, CoreMatchers.`is`(mockResponse))
          TestCase.assertEquals(it.data, CoreMatchers.`is`(updatedData.personDetail))
        }
        else -> MatcherAssert.assertThat(it, CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }
}
