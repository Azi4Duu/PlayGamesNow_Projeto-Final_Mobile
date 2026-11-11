package pt.atec.playgamesnow_mobile.viewmodel.sale

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import pt.atec.playgamesnow_mobile.api.RetrofitClient
import pt.atec.playgamesnow_mobile.model.sale.SaleRequest
import pt.atec.playgamesnow_mobile.model.sale.SaleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = RetrofitClient.getApi(application)

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var saleResponse by mutableStateOf<SaleResponse?>(null)
        private set

    fun createSale(request: SaleRequest) {

        if (loading) return

        loading = true
        error = null
        saleResponse = null

        apiService.createSale(request).enqueue(object : Callback<SaleResponse> {

            override fun onResponse(
                call: Call<SaleResponse>,
                response: Response<SaleResponse>
            ) {
                loading = false

                if (response.isSuccessful) {
                    saleResponse = response.body()
                    if (saleResponse == null) {
                        error = "Empty server response."
                    }
                } else {
                    error = when (response.code()) {
                        401 -> "Please log in to complete your purchase."
                        422 -> "Invalid payment details or insufficient stock."
                        else -> "Purchase failed. Error: ${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<SaleResponse>, t: Throwable) {
                loading = false
                error = t.message ?: "Connection error."
            }
        })
    }
}