package dev.jahidhasanco.firebasemvvm.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import dev.jahidhasanco.firebasemvvm.MainActivity
import dev.jahidhasanco.firebasemvvm.R
import dev.jahidhasanco.firebasemvvm.databinding.ActivitySplashScreenBinding
import dev.jahidhasanco.firebasemvvm.viewmodel.AuthViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        authViewModel.loggedUser()
        lifecycle.coroutineScope.launchWhenCreated {
            authViewModel.user.collect {
                if (it.isLoading) {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                if (it.error.isNotBlank()) {
                    Handler().postDelayed({
                        binding.progressCircular.visibility = View.GONE
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    }, 600)
                }
                it.data?.let {
                    Handler().postDelayed({
                        binding.progressCircular.visibility = View.GONE
                        startActivity(Intent(this@SplashScreenActivity, DashActivity::class.java))
                    }, 600)

                }
            }
        }

    }
}