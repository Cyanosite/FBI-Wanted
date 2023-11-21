package hu.bme.aut.android.fbiwanted.network

import hu.bme.aut.android.fbiwanted.model.WantedListData
import hu.bme.aut.android.fbiwanted.model.WantedPersonData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private val retrofit: Retrofit
    private val fbiApi: FbiApi

    private const val SERVICE_URL = "https://api.fbi.gov"

    init {
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("User-Agent", "BME VIK AUT")
                    builder.header("Accept", "application/json")
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        fbiApi = retrofit.create(FbiApi::class.java)
    }

    fun getWantedList(pageSize: Int = 10, page: Int = 1): Call<WantedListData?>? {
        return fbiApi.getWantedList(pageSize, page)
    }

    fun getWantedPerson(id: String?): Call<WantedPersonData?>? {
        return fbiApi.getWantedPerson(id)
    }
}