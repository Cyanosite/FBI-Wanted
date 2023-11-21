package hu.bme.aut.android.fbiwanted.network

import hu.bme.aut.android.fbiwanted.model.WantedListData
import hu.bme.aut.android.fbiwanted.model.WantedPersonData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FbiApi {
    @GET("@wanted")
    fun getWantedList(
        @Query("pageSize") pageSize: Int?,
        @Query("page") page: Int?,
        @Query("sort_on") sortOn: String = "modified",
        @Query("sort_order") sortOrder: String = "desc"
    ): Call<WantedListData?>?

    @GET("@wanted-person/{id}")
    fun getWantedPerson(@Path("id") id: String? = ""): Call<WantedPersonData?>?
}