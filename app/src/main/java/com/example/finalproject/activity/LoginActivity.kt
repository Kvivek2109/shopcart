package com.example.finalproject.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.finalproject.databinding.ActivityLoginBinding
import com.example.finalproject.util.Resource
import com.example.finalproject.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginViewModel: LoginViewModel by viewModels()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonLoginFragment.setOnClickListener {
                val email = edEmailLogin.text.toString().trim()
                val password = edPasswordLogin.text.toString()
                loginViewModel.login(applicationContext, email, password)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.login.collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            if (resource.data != null) {
                                val sharedPrefs = getSharedPreferences("myUser", Context.MODE_PRIVATE)
                                val editor: SharedPreferences.Editor = sharedPrefs.edit()
                                editor.putString("username", resource.data.username)
                                editor.apply()
                            }
                            Intent(this@LoginActivity, ShoppingActivity::class.java).also { intent ->
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                        }
                        is Resource.Error -> {
                            Toast.makeText(applicationContext, resource.message, Toast.LENGTH_LONG).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}