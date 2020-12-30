package sa.net.ibtikar.android.di

import androidx.room.Room
import sa.net.ibtikar.android.room.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val persistenceModule = module {
  single {
    Room
      .databaseBuilder(androidApplication(), AppDatabase::class.java, "TheMovies.db")
      .allowMainThreadQueries()
      .build()
  }

  single { get<AppDatabase>().peopleDao() }
}
