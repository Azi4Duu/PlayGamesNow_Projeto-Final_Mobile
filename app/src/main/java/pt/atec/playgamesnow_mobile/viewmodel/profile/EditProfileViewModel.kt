package pt.atec.playgamesnow_mobile.viewmodel.profile

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import pt.atec.playgamesnow_mobile.api.RetrofitClient
import pt.atec.playgamesnow_mobile.model.profile.ProfileUpdateRequest
import pt.atec.playgamesnow_mobile.model.profile.ProfileUpdateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = RetrofitClient.getApi(application)

    var name by mutableStateOf("")
        private set

    var username by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var loading by mutableStateOf(false)
        private set

    var saving by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var updated by mutableStateOf(false)
        private set

    fun updateName(value: String) {
        name = value
    }

    fun updateUsername(value: String) {
        username = value
    }

    fun updateEmail(value: String) {
        email = value
    }

    fun loadProfile() {

        loading = true
        error = null

        apiService.getProfile().enqueue(
            object : Callback<pt.atec.playgamesnow_mobile.model.profile.ProfileResponse> {

                override fun onResponse(
                    call: Call<pt.atec.playgamesnow_mobile.model.profile.ProfileResponse>,
                    response: Response<pt.atec.playgamesnow_mobile.model.profile.ProfileResponse>
                ) {
                    loading = false

                    if (response.isSuccessful) {
                        val profile = response.body()?.data

                        if (profile != null) {
                            name = profile.name
                            username = profile.username
                            email = profile.email
                        } else {
                            error = "Failed to load profile."
                        }
                    } else {
                        error = "Failed to load profile. Error: ${response.code()}"
                    }
                }

                override fun onFailure(
                    call: Call<pt.atec.playgamesnow_mobile.model.profile.ProfileResponse>,
                    t: Throwable
                ) {
                    loading = false
                    error = t.message ?: "Connection error."
                }
            }
        )
    }

    fun saveProfile() {

        if (saving || loading) return

        if (name.isBlank() || username.isBlank() || email.isBlank()) {
            error = "All fields are required."
            return
        }

        saving = true
        error = null
        updated = false

        val request = ProfileUpdateRequest(
            name = name.trim(),
            username = username.trim(),
            email = email.trim()
        )

        apiService.updateProfile(request).enqueue(
            object : Callback<ProfileUpdateResponse> {

                override fun onResponse(
                    call: Call<ProfileUpdateResponse>,
                    response: Response<ProfileUpdateResponse>
                ) {
                    saving = false

                    if (response.isSuccessful) {
                        updated = true
                    } else {
                        error = when (response.code()) {
                            401 -> "Please log in again."
                            422 -> "Invalid information. Check your email and username."
                            else -> "Failed to update profile. Error: ${response.code()}"
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ProfileUpdateResponse>,
                    t: Throwable
                ) {
                    saving = false
                    error = t.message ?: "Connection error."
                }
            }
        )
    }
}