package sa.net.ibtikar.android.api

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.SandwichInitializer
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiUtil {
  fun <T : Any> successCall(data: T) = createCall(Response.success(data))

  private fun <T : Any> createCall(response: Response<T>): ApiResponse<T> = ApiResponse.of(SandwichInitializer.successCodeRange) { response }

  fun <T> getCall(data: T) = object : Call<T> {
    override fun enqueue(callback: Callback<T>) = Unit
    override fun isExecuted() = false
    override fun clone(): Call<T> = this
    override fun isCanceled() = false
    override fun cancel() = Unit
    override fun request(): Request = Request.Builder().build()
    override fun execute(): Response<T> = Response.success(data)
  }
}
