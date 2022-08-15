package dev.jahidhasanco.firebasemvvm

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import dev.jahidhasanco.firebasemvvm.databinding.ActivityMainBinding
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


        authViewModel.getUserLiveData().observe(this) {
            if (it != null) {
                // Navigate to Other Activity
                this.displayToast("SuccessFully Pre-Logged")
            }
        }

        binding.authContainer.loginRegisterLogin.setOnClickListener {
            with(binding.authContainer) {
                email = loginRegisterEmail.text.toString()
                password = loginRegisterPassword.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    authViewModel.login(email, password)
                } else {
                    this@MainActivity.displayToast("Email and Password Must be Entered.")
                }
            }
        }

        binding.authContainer.loginRegisterRegister.setOnClickListener {
            with(binding.authContainer) {
                email = loginRegisterEmail.text.toString()
                password = loginRegisterPassword.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    authViewModel.register(email, password)
                } else {
                    this@MainActivity.displayToast("Email and Password Must be Entered.")
                }
            }
        }

    }

}