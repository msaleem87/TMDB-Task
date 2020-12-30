package sa.net.ibtikar.android.di

import sa.net.ibtikar.android.repository.PeopleRepository
import org.koin.dsl.module

val repositoryModule = module {
  single { PeopleRepository(get(), get()) }
}
