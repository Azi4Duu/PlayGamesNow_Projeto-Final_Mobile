package pt.atec.playgamesnow_mobile.viewmodel.profile

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import pt.atec.playgamesnow_mobile.api.RetrofitClient
import pt.atec.playgamesnow_mobile.model.profile.Profile
import pt.atec.playgamesnow_mobile.model.profile.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = RetrofitClient.getApi(application)

    var profile by mutableStateOf<Profile?>(null)
        private set

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun loadProfile() {

        loading = true
        error = null

        apiService.getProfile().enqueue(object : Callback<ProfileResponse> {

            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                loading = false

                if (response.isSuccessful) {

                    profile = response.body()?.data

                } else {

                    error = when (response.code()) {
                        401 -> "Please log in to view your profile."
                        else -> "Failed to load profile. Error: ${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {

                loading = false
                error = t.message ?: "Connection error."
            }
        })
    }
}