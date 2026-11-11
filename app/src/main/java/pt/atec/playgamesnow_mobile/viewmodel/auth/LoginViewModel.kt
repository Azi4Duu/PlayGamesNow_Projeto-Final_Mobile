package pt.atec.playgamesnow_mobile.viewmodel.auth

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import pt.atec.playgamesnow_mobile.api.RetrofitClient
import pt.atec.playgamesnow_mobile.data.TokenManager
import pt.atec.playgamesnow_mobile.model.auth.Login
import pt.atec.playgamesnow_mobile.model.auth.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenManager = TokenManager(application)

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var token by mutableStateOf<String?>(null)
        private set


    fun updateEmail(value: String) {
        email = value
    }

    fun updatePassword(value: String) {
        password = value
    }


    fun login() {

        loading = true
        error = null

        val login = Login(
            email = email,
            password = password
        )

        RetrofitClient
            .getApi(getApplication())
            .login(login)
            .enqueue(
                object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        loading = false
                        if (response.isSuccessful) {
                            response.body()?.let { result ->
                                token = result.token
                                tokenManager.saveToken(result.token)
                            }
                        } else {
                            error = when (response.code()) {
                                403 -> "Your account is not active."
                                422 -> "Incorrect email or password."
                                else -> "Login failed. Error: ${response.code()}"
                            }
                        }
                    }
                    override fun onFailure(
                        call: Call<LoginResponse>,
                        t: Throwable
                    ) {
                        loading = false
                        error = t.message
                    }
                }
            )
    }
}