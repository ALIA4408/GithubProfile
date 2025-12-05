package id.ac.polbeng.aliamaizatul.githubprofile.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.request.RequestOptions
import id.ac.polbeng.aliamaizatul.githubprofile.GlideApp
import id.ac.polbeng.aliamaizatul.githubprofile.R
import id.ac.polbeng.aliamaizatul.githubprofile.databinding.ActivityMainBinding
import id.ac.polbeng.aliamaizatul.githubprofile.helpers.Config
import id.ac.polbeng.aliamaizatul.githubprofile.models.GithubUser
import id.ac.polbeng.aliamaizatul.githubprofile.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.githubUser.observe(this) { user ->
            setUserData(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.btnSearchUserLogin.setOnClickListener {
            val userLogin = binding.etSearchUserLogin.text.toString()
            val query = if (userLogin.isNotEmpty()) userLogin else Config.DEFAULT_USER_LOGIN
            mainViewModel.searchUser(query)
        }
    }

    private fun setUserData(githubUser: GithubUser) {
        binding.tvUser.text = githubUser.toString()

        GlideApp.with(this)
            .load(githubUser.avatarUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
            )
            .into(binding.imgUser)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
