package sa.net.ibtikar.android.view.ui.details.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import sa.net.ibtikar.android.compose.DispatchViewModel
import sa.net.ibtikar.android.models.network.PersonDetail
import sa.net.ibtikar.android.repository.PeopleRepository
import timber.log.Timber

class PersonDetailViewModel constructor(private val peopleRepository: PeopleRepository) : DispatchViewModel() {

  private val personIdLiveData: MutableLiveData<Int> = MutableLiveData()
  val personLiveData: LiveData<PersonDetail>
  val toastLiveData: MutableLiveData<String> = MutableLiveData()

  init {
    Timber.d("Injection : PersonDetailViewModel")

    this.personLiveData = personIdLiveData.switchMap { id ->
      launchOnViewModelScope {
        peopleRepository.loadPersonDetail(id) { toastLiveData.postValue(it) }
      }
    }
  }

  fun postPersonId(id: Int) = personIdLiveData.postValue(id)

}
