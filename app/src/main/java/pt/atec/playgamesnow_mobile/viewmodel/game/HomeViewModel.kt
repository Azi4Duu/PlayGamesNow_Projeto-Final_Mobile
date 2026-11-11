package pt.atec.playgamesnow_mobile.viewmodel.game

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import pt.atec.playgamesnow_mobile.model.game.Platform
import pt.atec.playgamesnow_mobile.api.RetrofitClient
import pt.atec.playgamesnow_mobile.model.game.Category
import pt.atec.playgamesnow_mobile.model.game.GamePlatform
import pt.atec.playgamesnow_mobile.model.game.GamePlatformResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    var gamePlatforms by mutableStateOf<List<GamePlatform>>(emptyList())
        private set

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var search by mutableStateOf("")
        private set

    fun updateSearch(value: String) {
        search = value
    }

    var showFilters by mutableStateOf(false)
        private set

    fun toggleFilters() {
        showFilters = !showFilters
    }

    var activeFilter by mutableStateOf<String?>(null)
        private set

    fun openFilter(filter: String) {
        activeFilter = filter
    }

    fun closeFilter() {
        activeFilter = null
    }

    val platforms: List<Platform>
        get() = gamePlatforms.map { it.platform }.distinctBy {it.id}

    var selectedPlatforms by mutableStateOf<Set<Int>>(emptySet())
        private set

    val categories: List<Category>
        get() = gamePlatforms.flatMap { it.game.categories }.distinctBy { it.id }

    var selectedCategories by mutableStateOf<Set<Int>>(emptySet())
        private set

    var selectedStock by mutableStateOf<Set<String>>(emptySet())
        private set

    var minPrice by mutableStateOf("")
        private set

    var maxPrice by mutableStateOf("")
        private set

    fun updateMinPrice(value: String) {
        minPrice = value
    }

    fun updateMaxPrice(value: String) {
        maxPrice = value
    }

    fun loadGamePlatforms() {

        loading = true

        RetrofitClient.getApi(getApplication()).getGamePlatforms().enqueue(
            object : Callback<GamePlatformResponse> {
                override fun onResponse(
                    call: Call<GamePlatformResponse>,
                    response: Response<GamePlatformResponse>
                ) {
                    loading = false

                    if (response.isSuccessful) {
                        response.body()?.let { result ->
                            gamePlatforms = result.data
                            error = null
                        }
                    } else {
                        error = "Error loading games."
                    }
                }

                override fun onFailure(
                    call: Call<GamePlatformResponse>, t: Throwable
                ) {
                    loading = false

                    error = t.message
                }
            }
        )
    }

    fun togglePlatform(platformId: Int){
        selectedPlatforms =
            if(selectedPlatforms.contains(platformId)){
                selectedPlatforms - platformId
            }else{
                selectedPlatforms + platformId
            }
    }

    fun toggleCategory(categoryId: Int){
        selectedCategories =
            if (selectedCategories.contains(categoryId)){
                selectedCategories - categoryId
            }else{
                selectedCategories + categoryId
            }
    }

    fun toggleStock(stock: String){
        selectedStock =
            if (selectedStock.contains(stock)){
                selectedStock - stock
            }else{
                selectedStock + stock
            }
    }

    fun clearFilters() {
        selectedPlatforms = emptySet()
        selectedCategories = emptySet()
        selectedStock = emptySet()
        minPrice = ""
        maxPrice = ""
    }
}