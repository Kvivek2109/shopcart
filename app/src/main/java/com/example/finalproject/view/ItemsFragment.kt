package com.example.finalproject.view

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.adapters.ItemAdapter
import com.example.finalproject.databinding.FragmentItemsBinding
import com.example.finalproject.model.category.Category
import com.example.finalproject.util.Resource
import com.example.finalproject.viewmodel.ItemViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemsFragment : Fragment() {

    private lateinit var binding: FragmentItemsBinding
    private lateinit var itemAdapter: ItemAdapter
    private val viewModel by viewModels<ItemViewModel>()
    private val args by navArgs<ItemsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemsBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setTitle(R.string.title_items_fragment)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category : Category = args.category
        viewModel.fetchItemsByCategory(category)
        setupCategoryRv()

        itemAdapter.onClick = {
            val b = Bundle().apply { putParcelable("item", it) }
            findNavController().navigate(R.id.action_navigation_items_to_navigation_details, b)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.item.collectLatest {
                    when (it) {
                        is Resource.Success -> {
                            itemAdapter.differ.submitList(it.data)
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

    private fun setupCategoryRv() {
        itemAdapter = ItemAdapter()
        binding.rvItems.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = itemAdapter
        }
    }
}