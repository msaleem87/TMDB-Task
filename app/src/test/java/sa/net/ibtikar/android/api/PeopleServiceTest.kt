package sa.net.ibtikar.android.api

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.request
import sa.net.ibtikar.android.api.service.PeopleService
import java.io.IOException
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class PeopleServiceTest : ApiAbstract<PeopleService>() {

  private lateinit var service: PeopleService

  @Before
  fun initService() {
    this.service = createService(PeopleService::class.java)
  }

  @Throws(IOException::class)
  @Test
  fun fetchPersonListTest() {
    enqueueResponse("/tmdb_people.json")
    this.service.fetchPopularPeople(1).request {
      when (it) {
        is ApiResponse.Success -> {
          assertThat(it.data?.results?.get(0)?.id, `is`(28782))
          assertThat(it.data?.total_pages, `is`(984))
          assertThat(it.data?.total_results, `is`(19671))
        }
      }
    }
  }

  @Throws(IOException::class)
  @Test
  fun fetchPersonDetail() {
    enqueueResponse("tmdb_person.json")
    this.service.fetchPersonDetail(123).request {
      when (it) {
        is ApiResponse.Success -> {
          assertThat(it.data?.birthday, `is`("1963-12-18"))
          assertThat(it.data?.known_for_department, `is`("Acting"))
          assertThat(it.data?.place_of_birth, `is`("Shawnee, Oklahoma, USA"))
        }
      }
    }
  }
}
