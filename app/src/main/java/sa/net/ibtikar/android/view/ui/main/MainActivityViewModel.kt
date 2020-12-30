package sa.net.ibtikar.android.view.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import sa.net.ibtikar.android.compose.DispatchViewModel
import sa.net.ibtikar.android.models.entity.Person
import sa.net.ibtikar.android.repository.PeopleRepository
import timber.log.Timber

class MainActivityViewModel constructor(private val peopleRepository: PeopleRepository) : DispatchViewModel() {

  private var peoplePageLiveData: MutableLiveData<Int> = MutableLiveData()
  val peopleLiveData: LiveData<List<Person>>

  val toastLiveData: MutableLiveData<String> = MutableLiveData()

  init {
    Timber.d("injection MainActivityViewModel")

    this.peopleLiveData = peoplePageLiveData.switchMap { page ->
      launchOnViewModelScope {
        peopleRepository.loadPeople(page) { toastLiveData.postValue(it) }
      }
    }
  }

  fun postPeoplePage(page: Int) = peoplePageLiveData.postValue(page)
}
