package sa.net.ibtikar.android

import android.app.Application
import com.facebook.stetho.Stetho
import sa.net.ibtikar.android.di.networkModule
import sa.net.ibtikar.android.di.persistenceModule
import sa.net.ibtikar.android.di.repositoryModule
import sa.net.ibtikar.android.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@Suppress("unused")
class TheMoviesApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@TheMoviesApplication)
      modules(networkModule)
      modules(persistenceModule)
      modules(repositoryModule)
      modules(viewModelModule)
    }

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    Stetho.initializeWithDefaults(this)
  }
}
