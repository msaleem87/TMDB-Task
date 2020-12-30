package sa.net.ibtikar.android.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import sa.net.ibtikar.android.api.RequestInterceptor
import sa.net.ibtikar.android.api.service.PeopleService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
  single {
    OkHttpClient.Builder()
      .addInterceptor(RequestInterceptor())
      .addNetworkInterceptor(StethoInterceptor())
      .build()
  }

  single {
    Retrofit.Builder()
      .client(get<OkHttpClient>())
      .baseUrl("https://api.themoviedb.org/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  single {
    get<Retrofit>().create(PeopleService::class.java)
  }
}
