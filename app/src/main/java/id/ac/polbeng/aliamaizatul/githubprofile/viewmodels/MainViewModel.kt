package id.ac.polbeng.aliamaizatul.githubprofile.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.polbeng.aliamaizatul.githubprofile.helpers.Config
import id.ac.polbeng.aliamaizatul.githubprofile.models.GithubUser
import id.ac.polbeng.aliamaizatul.githubprofile.services.GithubUserService
import id.ac.polbeng.aliamaizatul.githubprofile.services.ServiceBuilder
import retrofit2.Call
class MainViewModel : ViewModel() {
    companion object {
        val TAG: String = MainViewModel::class.java.simpleName
    }
    private val _githubUser = MutableLiveData<GithubUser>()
    val githubUser: LiveData<GithubUser> = _githubUser
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        searchUser(Config.DEFAULT_USER_LOGIN)
    }
    fun searchUser(query: String) {
        _isLoading.value = true
        Log.d(TAG, "getDataUser: start...")

        val githubUserService: GithubUserService =
            ServiceBuilder.buildService(GithubUserService::class.java)
        val requestCall: Call<GithubUser> =
            githubUserService.loginUser(Config.PERSONAL_ACCESS_TOKEN, query)

        requestCall.enqueue(object : retrofit2.Callback<GithubUser> {
            override fun onResponse(
                call: Call<GithubUser>,
                response: retrofit2.Response<GithubUser>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubUser.postValue(response.body())
                } else {
                    Log.d(TAG, "API Failed Response")
                }
            }

            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "Error: ${t.message}")
            }
        })
    }
}