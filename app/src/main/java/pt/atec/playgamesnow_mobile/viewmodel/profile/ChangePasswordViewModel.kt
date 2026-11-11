package pt.atec.playgamesnow_mobile.viewmodel.profile

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import pt.atec.playgamesnow_mobile.api.RetrofitClient
import pt.atec.playgamesnow_mobile.model.profile.ChangePasswordRequest
import pt.atec.playgamesnow_mobile.model.profile.ChangePasswordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = RetrofitClient.getApi(application)

    var currentPassword by mutableStateOf("")
        private set

    var newPassword by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var updated by mutableStateOf(false)
        private set

    fun updateCurrentPassword(value: String) {
        currentPassword = value
    }

    fun updateNewPassword(value: String) {
        newPassword = value
    }

    fun updateConfirmPassword(value: String) {
        confirmPassword = value
    }

    fun changePassword() {

        if (loading) return

        if (
            currentPassword.isBlank() ||
            newPassword.isBlank() ||
            confirmPassword.isBlank()
        ) {
            error = "All fields are required."
            return
        }

        if (newPassword.length < 8) {
            error = "The new password must contain at least 8 characters."
            return
        }

        if (newPassword != confirmPassword) {
            error = "The passwords do not match."
            return
        }

        loading = true
        error = null
        updated = false

        val request = ChangePasswordRequest(
            current_password = currentPassword,
            password = newPassword,
            password_confirmation = confirmPassword
        )

        apiService.changePassword(request).enqueue(
            object : Callback<ChangePasswordResponse> {

                override fun onResponse(
                    call: Call<ChangePasswordResponse>,
                    response: Response<ChangePasswordResponse>
                ) {
                    loading = false

                    if (response.isSuccessful) {
                        currentPassword = ""
                        newPassword = ""
                        confirmPassword = ""
                        updated = true
                    } else {
                        error = when (response.code()) {
                            401 -> "Please log in again."
                            422 -> "Incorrect current password or invalid password details."
                            else -> "Failed to update password. Error: ${response.code()}"
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ChangePasswordResponse>,
                    t: Throwable
                ) {
                    loading = false
                    error = t.message ?: "Connection error."
                }
            }
        )
    }
}