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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.adapters.CategoryAdapter
import com.example.finalproject.databinding.FragmentCategoryBinding
import com.example.finalproject.util.Resource
import com.example.finalproject.viewmodel.CategoryViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private lateinit var binding: FragmentCategoryBinding

    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel by viewModels<CategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setTitle(R.string.title_category_fragment)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoryRv()

        categoryAdapter.onClick = {
            val b = Bundle().apply { putParcelable("category", it) }
            findNavController().navigate(R.id.action_navigation_category_to_navigation_items, b)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.category.collectLatest {
                    when (it) {
                        is Resource.Success -> {
                            categoryAdapter.differ.submitList(it.data)
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
        categoryAdapter = CategoryAdapter()
        binding.rvCategory.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }
}