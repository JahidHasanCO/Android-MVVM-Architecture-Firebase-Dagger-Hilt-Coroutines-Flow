package dev.jahidhasanco.firebasemvvm.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dev.jahidhasanco.firebasemvvm.R
import dev.jahidhasanco.firebasemvvm.databinding.ActivityDashBinding


class DashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_dash)
    }
}