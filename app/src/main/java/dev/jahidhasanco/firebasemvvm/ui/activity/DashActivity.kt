package dev.jahidhasanco.firebasemvvm.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import dagger.hilt.android.AndroidEntryPoint
import dev.jahidhasanco.firebasemvvm.MainActivity
import dev.jahidhasanco.firebasemvvm.R
import dev.jahidhasanco.firebasemvvm.databinding.ActivityDashBinding
import dev.jahidhasanco.firebasemvvm.utils.displayToast
import dev.jahidhasanco.firebasemvvm.viewmodel.LoggedInViewModel
import dev.jahidhasanco.firebasemvvm.viewmodel.UserViewModel

@AndroidEntryPoint
class DashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashBinding
    private val loggedInViewModel: LoggedInViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash)

        userViewModel.getUserData()

        lifecycle.coroutineScope.launchWhenCreated {
            userViewModel.userData.collect {
                if (it.isLoading) {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                if (it.error.isNotBlank()) {
                    binding.progressCircular.visibility = View.GONE
                    this@DashActivity.displayToast(it.error)
                }
                it.data?.let { _user ->
                    binding.progressCircular.visibility = View.GONE
                    binding.email.text = _user.email
                }
            }
        }

        binding.btnSignOut.setOnClickListener {
            loggedInViewModel.logOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}