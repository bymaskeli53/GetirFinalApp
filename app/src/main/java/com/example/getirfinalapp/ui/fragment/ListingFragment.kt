package com.example.getirfinalapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getirfinalapp.R
import com.example.getirfinalapp.adapter.BaseProductsAdapter
import com.example.getirfinalapp.adapter.ProductsAdapter
import com.example.getirfinalapp.adapter.SuggestedProductsAdapter
import com.example.getirfinalapp.data.model.GeneralProductItem
import com.example.getirfinalapp.data.model.SuggestedProductItem
import com.example.getirfinalapp.databinding.FragmentListingBinding
import com.example.getirfinalapp.network.ApiResult
import com.example.getirfinalapp.ui.viewmodel.BasketViewModel
import com.example.getirfinalapp.ui.viewmodel.ProductViewModel
import com.example.getirfinalapp.util.animatePrice
import com.example.getirfinalapp.util.hide
import com.example.getirfinalapp.util.invisible
import com.example.getirfinalapp.util.show
import com.example.getirfinalapp.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListingFragment : BaseFragment<FragmentListingBinding>() {

    private val viewModel: BasketViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()

    private var toolbar: Toolbar? = null

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentListingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        productViewModel.fetchProductList()
        productViewModel.fetchSuggestedProductList()

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
                productViewModel.products.collect { resource ->
                    when (resource) {
                        is ApiResult.Success -> {
                            binding.shimmerProducts.stopShimmer()
                            binding.shimmerProducts.hide()
                            binding.rvProducts.show()

                            val data = resource.data

                            if (data != null) {
                                val productsAdapter = ProductsAdapter(
                                    listener = object :
                                        BaseProductsAdapter.AddItemClickListener<GeneralProductItem> {
                                        override fun onAddItemClick(item: GeneralProductItem) {
                                            viewModel.increaseQuantity(item)
                                            viewModel.insertProductToLocal(item)
                                            viewModel.updateProductToLocal(item)
                                        }
                                    },
                                    items = data[0].products
                                )
                                binding.rvProducts.layoutManager =
                                    GridLayoutManager(context, SPAN_COUNT)
                                binding.rvProducts.adapter = productsAdapter
                            }
                        }

                        is ApiResult.Error -> {
                            showToast(resource.message)
                        }

                        is ApiResult.Loading -> {
                            binding.shimmerProducts.startShimmer()
                            binding.rvProducts.invisible()
                        }

                        ApiResult.NetworkError -> showToast(message = getString(R.string.no_internet_connection))
                        ApiResult.UnknownError -> showToast(message = getString(R.string.an_unknown_error_occurred))
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.suggestedProducts.collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Success -> {
                            val data = apiResult.data
                            if (data != null) {
                                val suggestedProductsAdapterItem =
                                    SuggestedProductsAdapter(
                                        listener = object :
                                            BaseProductsAdapter.AddItemClickListener<SuggestedProductItem> {
                                            override fun onAddItemClick(item: SuggestedProductItem) {
                                                viewModel.increaseQuantity(item)
                                                viewModel.insertProductToLocal(item)
                                                viewModel.updateProductToLocal(item)
                                            }
                                        },
                                        items = data[0].products,
                                        onItemClick = { position ->
                                            val action =
                                                ListingFragmentDirections.Companion.actionListingFragmentToDetailFragment(
                                                    apiResult.data[0].products[position]
                                                )
                                            findNavController().navigate(action)
                                        }
                                    )
                                binding.shimmerSuggested.stopShimmer()
                                binding.shimmerSuggested.hide()
                                binding.rvSuggested.show()
                                binding.rvSuggested.adapter = suggestedProductsAdapterItem
                                binding.rvSuggested.layoutManager = LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                                data[0].products[0].price?.times(
                                    (
                                            viewModel.quantity.value?.toDouble()
                                                ?: 0.0
                                            )
                                )
                            }
                        }

                        is ApiResult.Error -> {
                            showToast(apiResult.message)
                        }

                        is ApiResult.Loading -> {
                            binding.shimmerSuggested.startShimmer()
                            binding.rvSuggested.invisible()
                        }

                        ApiResult.NetworkError -> showToast(message = getString(R.string.no_internet_connection))
                        ApiResult.UnknownError -> showToast(message = getString(R.string.an_unknown_error_occurred))
                    }
                }
            }
        }
    }

    private fun setupToolbar() {
        toolbar = activity?.findViewById<Toolbar>(R.id.myToolbar)
        toolbar?.findViewById<ImageView>(R.id.iv_cancel)?.invisible()
        toolbar?.findViewById<LinearLayout>(R.id.toolbar_basket)?.show()
        toolbar?.findViewById<ImageView>(R.id.iv_delete)?.invisible()
        toolbar?.findViewById<TextView>(R.id.tv_toolbar_title)?.text = getString(R.string.products)
        toolbar?.findViewById<LinearLayout>(R.id.toolbar_basket)?.setOnClickListener {
            val action = ListingFragmentDirections.Companion.actionListingFragmentToBasketFragment()
            findNavController().navigate(action)
        }
    }

    companion object {
        private const val SPAN_COUNT = 3
    }
}
