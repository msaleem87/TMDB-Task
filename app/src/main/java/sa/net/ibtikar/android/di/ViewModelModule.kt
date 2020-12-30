package sa.net.ibtikar.android.di

import sa.net.ibtikar.android.view.ui.details.person.PersonDetailViewModel
import sa.net.ibtikar.android.view.ui.main.MainActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel { MainActivityViewModel(get()) }
  viewModel { PersonDetailViewModel(get()) }
}
