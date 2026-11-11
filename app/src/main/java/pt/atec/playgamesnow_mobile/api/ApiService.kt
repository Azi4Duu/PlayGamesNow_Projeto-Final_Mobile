package pt.atec.playgamesnow_mobile.api

import pt.atec.playgamesnow_mobile.model.cart.CartItemRequest
import pt.atec.playgamesnow_mobile.model.game.GamePlatformDetailResponse
import pt.atec.playgamesnow_mobile.model.game.GamePlatformResponse
import pt.atec.playgamesnow_mobile.model.auth.Login
import pt.atec.playgamesnow_mobile.model.cart.CartResponse
import pt.atec.playgamesnow_mobile.model.auth.LoginResponse
import pt.atec.playgamesnow_mobile.model.cart.CartItemUpdateRequest
import pt.atec.playgamesnow_mobile.model.cart.CouponRequest
import pt.atec.playgamesnow_mobile.model.cart.CouponResponse
import pt.atec.playgamesnow_mobile.model.profile.ChangePasswordRequest
import pt.atec.playgamesnow_mobile.model.profile.ChangePasswordResponse
import pt.atec.playgamesnow_mobile.model.profile.ProfileResponse
import pt.atec.playgamesnow_mobile.model.profile.ProfileUpdateRequest
import pt.atec.playgamesnow_mobile.model.profile.ProfileUpdateResponse
import pt.atec.playgamesnow_mobile.model.sale.PurchaseDetailResponse
import pt.atec.playgamesnow_mobile.model.sale.PurchasesResponse
import pt.atec.playgamesnow_mobile.model.sale.SaleRequest
import pt.atec.playgamesnow_mobile.model.sale.SaleResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("api/game-platforms")
    fun getGamePlatforms(): Call<GamePlatformResponse>

    @GET("api/game-platforms/{id}")
    fun getGamePlatform(@Path("id") id: Int): Call<GamePlatformDetailResponse>

    @POST("api/login")
    fun login(@Body login: Login): Call<LoginResponse>

    @GET("api/cart-items")
    fun getCartItems(): Call<CartResponse>

    @POST("api/cart-items")
    fun addToCart(@Body request: CartItemRequest): Call<Void>

    @PATCH("api/cart-items/{id}")
    fun updateCartItem(@Path("id") id: Int, @Body request: CartItemUpdateRequest): Call<Void>

    @DELETE("api/cart-items/{id}")
    fun removeCartItem(@Path("id") id: Int): Call<Void>


    @POST("api/cart/coupon/apply")
    fun applyCoupon(@Body request: CouponRequest): Call<CouponResponse>

    @DELETE("api/cart/coupon/remove")
    fun removeCoupon(): Call<CouponResponse>


    @POST("api/sales")
    fun createSale(@Body request: SaleRequest): Call<SaleResponse>

    @GET("api/profile")
    fun getProfile(): Call<ProfileResponse>

    @GET("api/sales")
    fun getPurchases(): Call<PurchasesResponse>

    @GET("api/sales/{id}")
    fun getPurchaseDetail(@Path("id") id: Int): Call<PurchaseDetailResponse>

    @PATCH("api/profile")
    fun updateProfile(@Body request: ProfileUpdateRequest): Call<ProfileUpdateResponse>

    @PATCH("api/profile/password")
    fun changePassword(@Body request: ChangePasswordRequest): Call<ChangePasswordResponse>

}