package dev.jahidhasanco.firebasemvvm.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import dev.jahidhasanco.firebasemvvm.MainActivity
import dev.jahidhasanco.firebasemvvm.R
import dev.jahidhasanco.firebasemvvm.databinding.ActivityDashBinding
import dev.jahidhasanco.firebasemvvm.viewmodel.LoggedInViewModel

@AndroidEntryPoint
class DashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashBinding
    private val loggedInViewModel: LoggedInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash)

        binding.btnSignOut.setOnClickListener {
            loggedInViewModel.logOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}