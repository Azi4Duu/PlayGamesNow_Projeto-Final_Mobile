package pt.atec.playgamesnow_mobile.api

import android.content.Context
import okhttp3.OkHttpClient
import pt.atec.playgamesnow_mobile.data.TokenManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.109:8000/"

    private var apiService: ApiService? = null

    fun getApi(context: Context): ApiService {

        if (apiService == null) {

            val tokenManager = TokenManager(context.applicationContext)

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(tokenManager))
                .build()

            apiService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

        return apiService!!
    }
}