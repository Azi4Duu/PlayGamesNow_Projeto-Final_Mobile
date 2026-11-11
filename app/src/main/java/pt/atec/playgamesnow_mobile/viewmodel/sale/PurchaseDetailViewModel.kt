package pt.atec.playgamesnow_mobile.viewmodel.sale

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import pt.atec.playgamesnow_mobile.api.RetrofitClient
import pt.atec.playgamesnow_mobile.model.sale.PurchaseDetail
import pt.atec.playgamesnow_mobile.model.sale.PurchaseDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchaseDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = RetrofitClient.getApi(application)

    var purchase by mutableStateOf<PurchaseDetail?>(null)
        private set

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun loadPurchaseDetail(id: Int) {

        loading = true
        error = null
        purchase = null

        apiService.getPurchaseDetail(id).enqueue(
            object : Callback<PurchaseDetailResponse> {

                override fun onResponse(
                    call: Call<PurchaseDetailResponse>,
                    response: Response<PurchaseDetailResponse>
                ) {
                    loading = false

                    if (response.isSuccessful) {
                        purchase = response.body()?.data

                        if (purchase == null) {
                            error = "Order details not found."
                        }
                    } else {
                        error = when (response.code()) {
                            401 -> "Please log in to view this order."
                            403 -> "You are not allowed to view this order."
                            404 -> "Order not found."
                            else -> "Failed to load order. Error: ${response.code()}"
                        }
                    }
                }

                override fun onFailure(
                    call: Call<PurchaseDetailResponse>,
                    t: Throwable
                ) {
                    loading = false
                    error = t.message ?: "Connection error."
                }
            }
        )
    }
}