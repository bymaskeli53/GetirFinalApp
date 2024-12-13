package com.example.getirfinalapp.ui.fragment

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getirfinalapp.R
import com.example.getirfinalapp.adapter.BasketAdapter
import com.example.getirfinalapp.data.model.GeneralProductItem
import com.example.getirfinalapp.databinding.FragmentBasketBinding
import com.example.getirfinalapp.databinding.HomeToolbarBinding
import com.example.getirfinalapp.ui.activity.MainActivity
import com.example.getirfinalapp.ui.viewmodel.BasketViewModel
import com.example.getirfinalapp.util.autoCleared
import com.example.getirfinalapp.util.hide
import com.example.getirfinalapp.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : Fragment(R.layout.fragment_basket) {

    private var binding: FragmentBasketBinding by autoCleared()

    private val viewModel: BasketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = activity?.findViewById<Toolbar>(R.id.myToolbar)

        val toolbarBinding = HomeToolbarBinding.bind(requireActivity().findViewById(R.id.myToolbar))
        toolbarBinding.tvToolbarTitle.text = getString(R.string.my_basket)

        toolbarBinding.ivDelete.show()
        toolbarBinding.toolbarBasket.root.hide()
        toolbarBinding.ivCancel.show()

        toolbarBinding.ivCancel.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        viewModel.getProductsFromLocal()

        viewModel.productsInBasket.observe(viewLifecycleOwner, {
            createBasketAdapter(it)
        })

        toolbar?.findViewById<ImageView>(R.id.iv_delete)?.setOnClickListener {
            createClearBasketDialog()
        }

        makeStrikeThroughText(binding.tvDiscountPrice)
    }

    private fun createBasketAdapter(products: List<GeneralProductItem>) {
        val adapter = BasketAdapter(products)
        binding.rvBasket.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBasket.adapter = adapter
    }

    private fun createClearBasketDialog() {
        if (viewModel.productsInBasket.value?.size != 0) {
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Sepeti boşaltmak istiyor musunuz?")
                .setNegativeButton("Evet") { dialog, view ->
                    viewModel.clearBasket()

                    (requireActivity() as? MainActivity)?.updateToolbarPrice(0.0)

                    // viewModel.getProductsFromLocal()

                    dialog.dismiss()
                }
                .setPositiveButton("Hayır") { dialog, _ ->
                    dialog.dismiss()
                }.create()
            dialog.show()
        }
    }

    private fun makeStrikeThroughText(textView: TextView) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}
