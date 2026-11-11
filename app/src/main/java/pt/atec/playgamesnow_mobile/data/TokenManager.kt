package pt.atec.playgamesnow_mobile.data

import android.content.Context
import androidx.core.content.edit

class TokenManager(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(
            "auth",
            Context.MODE_PRIVATE
        )

    fun saveToken(token: String) {
        sharedPreferences
            .edit {
                putString("token", token)
            }
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun clearToken() {
        sharedPreferences
            .edit {
                remove("token")
            }
    }
}