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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.adapters.CartAdapter
import com.example.finalproject.databinding.FragmentCartBinding
import com.example.finalproject.model.cart.CartRepository
import com.example.finalproject.model.cart.CartRoomDatabase
import com.example.finalproject.util.Resource
import com.example.finalproject.viewmodel.CartViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel by viewModels<CartViewModel>()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setTitle(R.string.title_cart)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUsername()
        setupCartRv()
        val cartRepository =
            CartRepository(CartRoomDatabase.getInstance(requireContext()).cartDao())
        viewModel.initCartRepository(cartRepository)
        viewModel.getCartItems()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cartItem.collectLatest {
                    when (it) {
                        is Resource.Success -> {
                            if (it.data!!.isEmpty()) {
                                showEmptyCart()
                                hideOtherViews()
                            } else {
                                hideEmptyCart()
                                showOtherViews()
                                cartAdapter.differ.submitList(it.data)
                            }
                        }
                        is Resource.Error -> {
                            showEmptyCart()
                            hideOtherViews()
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }
        }

        cartAdapter.onIncreaseClick = {
            viewModel.increaseQuantity(it)
        }

        cartAdapter.onDecreaseClick = {
            viewModel.decreaseQuantity(it)
        }

        cartAdapter.onDeleteClick = {
            viewModel.deleteCartItem(it)
        }
    }

    private fun showOtherViews() {
        binding.apply {
            rvCart.visibility = View.VISIBLE
        }
    }

    private fun hideOtherViews() {
        binding.apply {
            rvCart.visibility = View.GONE
        }
    }

    private fun hideEmptyCart() {
        binding.apply {
            layoutCartEmpty.visibility = View.GONE
        }
    }

    private fun showEmptyCart() {
        binding.apply {
            layoutCartEmpty.visibility = View.VISIBLE
        }
    }

    private fun setupCartRv() {
        cartAdapter = CartAdapter()
        binding.rvCart.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = cartAdapter
        }
    }

    private fun loadUsername() {
        val sharedPrefs = requireActivity().getSharedPreferences("myUser", Context.MODE_PRIVATE)
        val username = sharedPrefs.getString("username", "") ?: ""
        viewModel.setUsername(username)
    }
}