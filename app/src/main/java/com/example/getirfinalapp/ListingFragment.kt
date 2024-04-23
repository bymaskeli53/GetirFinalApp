package com.example.getirfinalapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getirfinalapp.databinding.FragmentListingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListingFragment : Fragment(R.layout.fragment_listing) {

    private var binding: FragmentListingBinding by autoCleared()

    private val viewModel: GetirViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = activity?.findViewById<Toolbar>(R.id.myToolbar)
        toolbar?.findViewById<ImageView>(R.id.iv_cancel)?.visibility = View.INVISIBLE

        viewModel.fetchData()
        viewModel.fetchSuggestedData()


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val data = resource.data
                            // Recycler View veri gösterilecek.
                            if (data != null) {
                                val productsAdapter = ProductsAdapter(data!!)
                                binding.rvProducts.layoutManager = GridLayoutManager(context,3)
                                binding.rvProducts.adapter = productsAdapter


                            }


                        }

                        is Resource.Error -> {
                            val errorMessage = resource.message
                        }

                        is Resource.Loading -> {
                           print("Hi")
                        }
                    }

                }


            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.suggestedProducts.collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val data = resource.data
                            if (data != null) {
                                val suggestedProductsAdapter = SuggestedProductsAdapter(data!!)
                                binding.rvSuggested.adapter = suggestedProductsAdapter
                                binding.rvSuggested.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            }
                        }

                        is Resource.Error -> {
                            val errorMessage = resource.message
                        }

                        is Resource.Loading -> {
                            // Handle loading state if needed
                        }
                    }
                }
            }

    }


}
}