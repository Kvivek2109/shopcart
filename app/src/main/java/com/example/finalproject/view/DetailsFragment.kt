package com.example.finalproject.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentDetailsBinding
import com.example.finalproject.util.Resource
import com.example.finalproject.viewmodel.DetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel by viewModels<DetailsViewModel>()
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setTitle(R.string.title_details_fragment)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = args.item

        binding.apply {
            val resourceId = binding.root.context.resources.getIdentifier(item.src, "drawable", binding.root.context.packageName)
            productImage.setImageResource(resourceId)
            Glide.with(binding.productImage).load(item.src).into(productImage)
            productName.text = item.name
            productPrice.text = "$ ${item.price}"
        }

        binding.buttonAddToCart.setOnClickListener {
            username = loadUsername()
            viewModel.addProductInCart(requireContext(), username, item.id, 1)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addToCart.collectLatest {
                    when (it) {
                        is Resource.Success -> {
                            binding.buttonAddToCart.text = "Added to Cart"
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun loadUsername(): String {
        val sharedPrefs = requireActivity().getSharedPreferences("myUser", Context.MODE_PRIVATE)
        return sharedPrefs.getString("username", "") ?: ""
    }
}