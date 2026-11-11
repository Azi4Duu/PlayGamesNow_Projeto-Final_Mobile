package pt.atec.playgamesnow_mobile.viewmodel.cart

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import pt.atec.playgamesnow_mobile.api.RetrofitClient
import pt.atec.playgamesnow_mobile.model.cart.CartItem
import pt.atec.playgamesnow_mobile.model.cart.CartItemUpdateRequest
import pt.atec.playgamesnow_mobile.model.cart.CartResponse
import pt.atec.playgamesnow_mobile.model.cart.Coupon
import pt.atec.playgamesnow_mobile.model.cart.CouponRequest
import pt.atec.playgamesnow_mobile.model.cart.CouponResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel(
    application: Application
) : AndroidViewModel(application) {

    var cartItems by mutableStateOf<List<CartItem>>(emptyList())
        private set

    var subtotal by mutableDoubleStateOf(0.0)
        private set


    var discount by mutableDoubleStateOf(0.0)
        private set

    var total by mutableDoubleStateOf(0.0)
        private set

    var coupon by mutableStateOf<Coupon?>(null)
        private set

    var couponCode by mutableStateOf("")
        private set

    var couponLoading by mutableStateOf(false)
        private set

    var couponError by mutableStateOf<String?>(null)
        private set

    var couponSuccess by mutableStateOf<String?>(null)
        private set

    fun updateCouponCode(value: String) {
        couponCode = value
        couponError = null
        couponSuccess = null
    }


    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun increaseQuantity(cartItem: CartItem) {
        updateQuantity(
            cartItemId = cartItem.id,
            quantity = cartItem.quantity + 1
        )
    }

    fun decreaseQuantity(cartItem: CartItem) {
        if (cartItem.quantity > 1) {
            updateQuantity(
                cartItemId = cartItem.id,
                quantity = cartItem.quantity - 1
            )
        } else {
            removeCartItem(cartItem.id)
        }
    }

    fun loadCart() {

        loading = true
        error = null

        RetrofitClient
            .getApi(getApplication())
            .getCartItems()
            .enqueue(
                object : Callback<CartResponse> {

                    override fun onResponse(
                        call: Call<CartResponse>,
                        response: Response<CartResponse>
                    ) {
                        loading = false

                        if (response.isSuccessful) {

                            response.body()?.let { result ->
                                cartItems = result.items
                                subtotal = result.subtotal
                                coupon = result.coupon
                                discount = result.discount
                                total = result.total.takeIf {
                                    it != 0.0 || result.subtotal == 0.0
                                } ?: result.subtotal
                            }

                        } else {
                            error = "Error loading cart."
                        }
                    }

                    override fun onFailure(
                        call: Call<CartResponse>,
                        t: Throwable
                    ) {
                        loading = false
                        error = t.message
                    }
                }
            )
    }

    private fun updateQuantity(
        cartItemId: Int,
        quantity: Int
    ) {

        val request = CartItemUpdateRequest(
            quantity = quantity
        )

        RetrofitClient
            .getApi(getApplication())
            .updateCartItem(
                id = cartItemId,
                request = request
            )
            .enqueue(
                object : Callback<Void> {

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {

                        if (response.isSuccessful) {
                            error = null
                            loadCart()
                        } else {
                            error = "Error updating cart."
                        }
                    }

                    override fun onFailure(
                        call: Call<Void>,
                        t: Throwable
                    ) {
                        error = t.message
                    }
                }
            )
    }

    private fun removeCartItem(cartItemId: Int) {

        RetrofitClient
            .getApi(getApplication())
            .removeCartItem(cartItemId)
            .enqueue(
                object : Callback<Void> {

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {

                        if (response.isSuccessful) {
                            error = null
                            loadCart()
                        } else {
                            error = "Error removing item from cart."
                        }
                    }

                    override fun onFailure(
                        call: Call<Void>,
                        t: Throwable
                    ) {
                        error = t.message
                    }
                }
            )
    }


    fun applyCoupon() {
        val code = couponCode.trim()

        if (code.isEmpty()) {
            couponError = "Please enter a coupon code."
            return
        }

        if (couponLoading) return

        couponLoading = true
        couponError = null
        couponSuccess = null

        RetrofitClient
            .getApi(getApplication())
            .applyCoupon(CouponRequest(code))
            .enqueue(object : Callback<CouponResponse> {

                override fun onResponse(
                    call: Call<CouponResponse>,
                    response: Response<CouponResponse>
                ) {
                    couponLoading = false

                    if (response.isSuccessful) {
                        couponCode = ""
                        couponSuccess = response.body()?.message
                            ?: "Coupon applied successfully."
                        loadCart()
                    } else {
                        couponError = readCouponError(response)
                    }
                }

                override fun onFailure(
                    call: Call<CouponResponse>,
                    t: Throwable
                ) {
                    couponLoading = false
                    couponError = "Connection error. Please try again."
                }
            })
    }

    fun removeCoupon() {
        if (couponLoading) return

        couponLoading = true
        couponError = null
        couponSuccess = null

        RetrofitClient
            .getApi(getApplication())
            .removeCoupon()
            .enqueue(object : Callback<CouponResponse> {

                override fun onResponse(
                    call: Call<CouponResponse>,
                    response: Response<CouponResponse>
                ) {
                    couponLoading = false

                    if (response.isSuccessful) {
                        couponSuccess = response.body()?.message
                            ?: "Coupon removed successfully."
                        loadCart()
                    } else {
                        couponError = readCouponError(response)
                    }
                }

                override fun onFailure(
                    call: Call<CouponResponse>,
                    t: Throwable
                ) {
                    couponLoading = false
                    couponError = "Connection error. Please try again."
                }
            })
    }

    private fun readCouponError(
        response: Response<CouponResponse>
    ): String {
        val errorBody = response.errorBody()?.string()

        return try {
            JSONObject(errorBody ?: "")
                .optString("message", "Coupon operation failed.")
        } catch (e: Exception) {
            "Coupon operation failed."
        }
    }

}