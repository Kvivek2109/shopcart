package com.example.finalproject.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.finalproject.R
import com.example.finalproject.activity.LoginActivity
import com.example.finalproject.databinding.FragmentProfileBinding
import com.example.finalproject.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        loadUsername()
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.textUsername.text = viewModel.username.value
        (requireActivity() as AppCompatActivity).supportActionBar!!.setTitle(R.string.title_profile)
        binding.buttonLogout.setOnClickListener {
            logout()
        }
        return binding.root
    }

    private fun loadUsername() {
        val sharedPrefs = requireActivity().getSharedPreferences("myUser", Context.MODE_PRIVATE)
        val username = sharedPrefs.getString("username", "") ?: ""
        viewModel.setUsername(username)
    }

    private fun logout() {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", null)
        editor.apply()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }
}