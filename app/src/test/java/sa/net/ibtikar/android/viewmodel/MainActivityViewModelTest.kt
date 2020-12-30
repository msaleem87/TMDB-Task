package sa.net.ibtikar.android.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import sa.net.ibtikar.android.MainCoroutinesRule
import sa.net.ibtikar.android.api.ApiUtil
import sa.net.ibtikar.android.api.service.PeopleService
import sa.net.ibtikar.android.models.entity.Person
import sa.net.ibtikar.android.models.network.PeopleResponse
import sa.net.ibtikar.android.repository.PeopleRepository
import sa.net.ibtikar.android.room.PeopleDao
import sa.net.ibtikar.android.utils.MockTestUtil.Companion.mockPersonList
import sa.net.ibtikar.android.view.ui.main.MainActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {

  private lateinit var viewModel: MainActivityViewModel
  private lateinit var peopleRepository: PeopleRepository
  private val peopleService = mock<PeopleService>()
  private val peopleDao = mock<PeopleDao>()

  @ExperimentalCoroutinesApi
  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    this.peopleRepository = PeopleRepository(peopleService, peopleDao)
    this.viewModel = MainActivityViewModel(peopleRepository)
  }

  @Test
  fun loadPeopleFromNetwork() = runBlocking {
    val loadFromDB = emptyList<Person>()
    whenever(peopleDao.getPeople(1)).thenReturn(loadFromDB)

    val mockResponse = PeopleResponse(1, mockPersonList(), 100, 10)
    whenever(peopleService.fetchPopularPeople(1)).thenReturn(ApiUtil.getCall(mockResponse))

    val data = viewModel.peopleLiveData
    val observer = mock<Observer<List<Person>>>()
    data.observeForever(observer)

    viewModel.postPeoplePage(1)
    viewModel.postPeoplePage(1)

    verify(peopleDao, atLeastOnce()).getPeople(1)
    verify(peopleService, atLeastOnce()).fetchPopularPeople(1)
    verify(observer, atLeastOnce()).onChanged(loadFromDB)
    data.removeObserver(observer)
  }
}
