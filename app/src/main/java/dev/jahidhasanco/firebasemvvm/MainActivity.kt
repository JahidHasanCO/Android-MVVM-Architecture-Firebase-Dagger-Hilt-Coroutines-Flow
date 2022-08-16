package dev.jahidhasanco.firebasemvvm

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import dagger.hilt.android.AndroidEntryPoint
import dev.jahidhasanco.firebasemvvm.data.model.User
import dev.jahidhasanco.firebasemvvm.databinding.ActivityMainBinding
import dev.jahidhasanco.firebasemvvm.ui.activity.DashActivity
import dev.jahidhasanco.firebasemvvm.utils.displayToast
import dev.jahidhasanco.firebasemvvm.viewmodel.AuthViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val authViewModel: AuthViewModel by viewModels()

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        authViewModel.loggedUser()
        lifecycle.coroutineScope.launchWhenCreated {
            authViewModel.user.collect {
                if (it.isLoading) {
                    binding.authContainer.progressCircular.visibility = View.VISIBLE
                }
                if (it.error.isNotBlank()) {
                    binding.authContainer.progressCircular.visibility = View.GONE
                    this@MainActivity.displayToast(it.error)
                }
                it.data?.let {
                    binding.authContainer.progressCircular.visibility = View.GONE
                    startActivity(Intent(this@MainActivity, DashActivity::class.java))
                }
            }
        }

        binding.authContainer.btnSignIn.setOnClickListener {
            with(binding.authContainer) {
                email = edtEmailID.text.toString()
                password = edtPassword.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    authViewModel.login(email, password)
                } else {
                    this@MainActivity.displayToast("Email and Password Must be Entered.")
                }
            }
        }

        binding.authContainer.btnSignUp.setOnClickListener {
            with(binding.authContainer) {
                email = edtEmailID.text.toString()
                password = edtPassword.text.toString()

                val user = User(
                    name = "Jahid Hasan",
                    image = "",
                    email = email,
                    active = true,
                    address = "Dhaka, Bangladesh"
                )

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    authViewModel.register(email, password, user)
                } else {
                    this@MainActivity.displayToast("Email and Password Must be Entered.")
                }
            }
        }

    }


}