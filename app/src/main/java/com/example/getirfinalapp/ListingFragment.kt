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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getirfinalapp.databinding.FragmentListingBinding
import dagger.hilt.android.AndroidEntryPoint
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
        toolbar?.findViewById<ImageView>(R.id.iv_cancel)?.invisible()
        toolbar?.findViewById<LinearLayout>(R.id.toolbar_basket)?.show()
        toolbar?.findViewById<ImageView>(R.id.iv_delete)?.invisible()
        toolbar?.findViewById<TextView>(R.id.tv_toolbar_title)?.text = "Ürünler"





        toolbar?.findViewById<LinearLayout>(R.id.toolbar_basket)?.setOnClickListener {
            val action = ListingFragmentDirections.actionListingFragmentToBasketFragment()
            findNavController().navigate(action)


        }

        viewModel.fetchData()
        viewModel.fetchSuggestedData()

        viewModel.quantity.observe(viewLifecycleOwner, {
            binding.rvSuggested.rootView.findViewById<ImageView>(R.id.iv_plus)
        })


        viewModel.totalPrice.observe(viewLifecycleOwner, {
            val precisedTotalPrice =
                Math.round(viewModel.totalPrice.value?.times(1000.0) ?: 1.0) / 1000.0
            toolbar?.findViewById<LinearLayout>(R.id.toolbar_basket)
                ?.findViewById<TextView>(R.id.tv_totalPrice)?.animatePrice(precisedTotalPrice)
        })

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collect { resource ->
                    when (resource) {
                        is ApiResult.Success -> {
                            binding.shimmerProducts.stopShimmer()
                            binding.shimmerProducts.hide()
                            binding.rvProducts.show()

                            val data = resource.data
                            // Recycler View veri gösterilecek.
                            if (data != null) {
                                //   val productsAdapter = ProductsAdapter(this@ListingFragment,data!!)

                                val productsAdapter = ProductsAdapter(listener = object :
                                    BaseProductsAdapter.AddItemClickListener<ProductXX> {
                                    override fun onAddItemClick(item: ProductXX) {
                                        viewModel.increaseQuantity(item)
                                        viewModel.insertProductToLocal(item)
                                        viewModel.updateProductToLocal(item)
                                    }
                                }, items = data!![0].products)
                                binding.rvProducts.layoutManager = GridLayoutManager(context, 3)
                                binding.rvProducts.adapter = productsAdapter


                            }


                        }

                        is ApiResult.Error -> {
                            val errorMessage = resource.message
                        }

                        is ApiResult.Loading -> {
                            binding.shimmerProducts.startShimmer()
                            binding.rvProducts.invisible()
                        }
                    }

                }


            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.suggestedProducts.collect { resource ->
                    when (resource) {
                        is ApiResult.Success -> {
                            val data = resource.data
                            if (data != null) {



                                val suggestedProductsAdapter =
                                    SuggestedProductsAdapter(listener = object :
                                        BaseProductsAdapter.AddItemClickListener<ProductX> {
                                        override fun onAddItemClick(item: ProductX) {
                                            viewModel.increaseQuantity(item)
                                            viewModel.insertProductToLocal(item)
                                            viewModel.updateProductToLocal(item)
                                        }
                                    }, items = data!![0].products, onItemClick = { position ->
                                        val action =
                                            ListingFragmentDirections.actionListingFragmentToDetailFragment(
                                                resource.data[0].products[position]
                                            )
                                        findNavController().navigate(action)
                                    })
                                binding.shimmerSuggested.stopShimmer()
                                binding.shimmerSuggested.hide()
                                binding.rvSuggested.show()
                                binding.rvSuggested.adapter = suggestedProductsAdapter
                                binding.rvSuggested.layoutManager = LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                                data[0].products[0].price?.times(
                                    (viewModel.quantity.value?.toDouble()
                                        ?: 0.0)
                                )
                            }
                        }

                        is ApiResult.Error -> {

                        }

                        is ApiResult.Loading -> {
                            binding.shimmerSuggested.startShimmer()
                            binding.rvSuggested.invisible()

                        }
                    }
                }
            }
        }
    }
}