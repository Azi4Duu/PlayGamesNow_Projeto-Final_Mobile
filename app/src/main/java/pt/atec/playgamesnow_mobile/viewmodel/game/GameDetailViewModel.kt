package pt.atec.playgamesnow_mobile.viewmodel.game

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import pt.atec.playgamesnow_mobile.api.RetrofitClient
import pt.atec.playgamesnow_mobile.data.TokenManager
import pt.atec.playgamesnow_mobile.model.cart.CartItemRequest
import pt.atec.playgamesnow_mobile.model.game.GamePlatform
import pt.atec.playgamesnow_mobile.model.game.GamePlatformDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameDetailViewModel(application: Application) : AndroidViewModel(application) {

    var gamePlatform by mutableStateOf<GamePlatform?>(null)
        private set

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    private val tokenManager = TokenManager(getApplication())

    fun isLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }

    var addedToCart by mutableStateOf(false)
        private set

    var quantity by mutableIntStateOf(1)
        private set

    fun increaseQuantity() {

        val gamePlatform = gamePlatform ?: return

        if (quantity < gamePlatform.stock) {
            quantity++
        }
    }

    fun decreaseQuantity() {
        if (quantity > 1) {
            quantity--
        }
    }

    fun addToCart(gamePlatformId: Int) {

        val request = CartItemRequest(
            game_platform_id = gamePlatformId,
            quantity = quantity
        )

        RetrofitClient
            .getApi(getApplication())
            .addToCart(request)
            .enqueue(
                object : Callback<Void> {

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (response.isSuccessful) {
                            addedToCart = true
                            error = null
                            loadGamePlatform(gamePlatformId)
                        } else {
                            error = "Erro ${response.code()}: ${response.errorBody()?.string()}"
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        error = t.message
                    }
                }
            )
    }

    fun loadGamePlatform(id: Int){
        loading = true

        RetrofitClient.getApi(getApplication()).getGamePlatform(id).enqueue(
            object : Callback<GamePlatformDetailResponse>{
                override fun onResponse(
                    call: Call<GamePlatformDetailResponse?>,
                    response: Response<GamePlatformDetailResponse?>
                ) {
                    loading = false

                    if(response.isSuccessful){
                        response.body()?.let {result ->
                            gamePlatform = result.data
                            error = null
                        }
                    }else{
                        error = "Error loading game"
                    }
                }

                override fun onFailure(call: Call<GamePlatformDetailResponse?>, t: Throwable) {
                    loading = false
                    error = t.message
                }
            }
        )
    }
}