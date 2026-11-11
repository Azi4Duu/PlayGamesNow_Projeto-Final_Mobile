package pt.atec.playgamesnow_mobile.viewmodel.sale

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import pt.atec.playgamesnow_mobile.api.RetrofitClient
import pt.atec.playgamesnow_mobile.model.sale.Purchase
import pt.atec.playgamesnow_mobile.model.sale.PurchasesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchasesViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = RetrofitClient.getApi(application)

    var purchases by mutableStateOf<List<Purchase>>(emptyList())
        private set

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun loadPurchases() {

        loading = true
        error = null

        apiService.getPurchases().enqueue(object : Callback<PurchasesResponse> {

            override fun onResponse(
                call: Call<PurchasesResponse>,
                response: Response<PurchasesResponse>
            ) {
                loading = false

                if (response.isSuccessful) {
                    purchases = response.body()?.data ?: emptyList()
                } else {
                    error = when (response.code()) {
                        401 -> "Please log in to view your purchases."
                        else -> "Failed to load purchases. Error: ${response.code()}"
                    }
                }
            }

            override fun onFailure(
                call: Call<PurchasesResponse>,
                t: Throwable
            ) {
                loading = false
                error = t.message ?: "Connection error."
            }
        })
    }
}