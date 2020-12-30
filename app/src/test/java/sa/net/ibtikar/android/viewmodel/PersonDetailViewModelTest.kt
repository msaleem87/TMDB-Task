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
import sa.net.ibtikar.android.models.network.PersonDetail
import sa.net.ibtikar.android.repository.PeopleRepository
import sa.net.ibtikar.android.room.PeopleDao
import sa.net.ibtikar.android.utils.MockTestUtil
import sa.net.ibtikar.android.view.ui.details.person.PersonDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PersonDetailViewModelTest {

  private lateinit var viewModel: PersonDetailViewModel
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
    this.repository = PeopleRepository(service, peopleDao)
    this.viewModel = PersonDetailViewModel(repository)
  }

  @Test
  fun loadPersonDetailFromLocalDatabase() {
    coroutinesRule.runBlockingTest {
      val mockResponse = MockTestUtil.mockPersonDetail()
      whenever(service.fetchPersonDetail(1)).thenReturn(ApiUtil.getCall(mockResponse))
      whenever(peopleDao.getPerson(1)).thenReturn(MockTestUtil.mockPerson())

      val data = viewModel.personLiveData
      val observer = mock<Observer<PersonDetail>>()
      data.observeForever(observer)

      viewModel.postPersonId(1)
      viewModel.postPersonId(1)

      verify(peopleDao, atLeastOnce()).getPerson(1)
      verify(observer, atLeastOnce()).onChanged(mockResponse)
      data.removeObserver(observer)
    }
  }
}
