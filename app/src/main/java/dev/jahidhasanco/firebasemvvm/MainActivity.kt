package dev.jahidhasanco.firebasemvvm

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import dev.jahidhasanco.firebasemvvm.data.model.User
import dev.jahidhasanco.firebasemvvm.databinding.ActivityMainBinding
import dev.jahidhasanco.firebasemvvm.ui.activity.DashActivity
import dev.jahidhasanco.firebasemvvm.utils.displayToast
import dev.jahidhasanco.firebasemvvm.viewmodel.AuthViewModel
import dev.jahidhasanco.firebasemvvm.viewmodel.UserViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        authViewModel.getUserLiveData().observe(this) {
            if (it != null) {
                // Navigate to Other Activity
                startActivity(Intent(this, DashActivity::class.java))
                finish()
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
                    name = "",
                    image = "",
                    email = email,
                    active = true,
                    address = ""
                )

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    authViewModel.register(email, password)
                    userDataStoreRemote(user)
                } else {
                    this@MainActivity.displayToast("Email and Password Must be Entered.")
                }
            }
        }

    }

    private fun userDataStoreRemote(user: User) {
        userViewModel.uploadUserData(user)
    }

}