package com.example.getirfinalapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getirfinalapp.databinding.FragmentBasketBinding
import com.example.getirfinalapp.databinding.HomeToolbarBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BasketFragment : Fragment(R.layout.fragment_basket) {

    private var binding: FragmentBasketBinding by autoCleared()

    private val viewModel: GetirViewModel by viewModels()

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
        toolbarBinding.tvToolbarTitle.text = "Sepetim"

//        val toolbarText = toolbar?.findViewById<TextView>(R.id.tv_toolbar_title)
//        toolbarText?.text = "Sepetim"

       // toolbar?.findViewById<ImageView>(R.id.iv_delete)?.visibility = View.VISIBLE
        toolbarBinding.ivDelete.visibility = View.VISIBLE

      // toolbar?.findViewById<LinearLayout>(R.id.toolbar_basket)?.visibility = View.GONE
        toolbarBinding.toolbarBasket.root.visibility = View.GONE

       // toolbar?.findViewById<ImageView>(R.id.iv_cancel)?.visibility = View.VISIBLE
        toolbarBinding.ivCancel.visibility = View.VISIBLE

//        toolbar?.findViewById<ImageView>(R.id.iv_cancel)?.setOnClickListener {
//            requireActivity().supportFragmentManager.popBackStack()
//        }

        toolbarBinding.ivCancel.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }



        viewModel.getProductsFromLocal()
        // viewModel.deleteProductsFromLocal()

        viewModel.productsInBasket.observe(viewLifecycleOwner, {
            val adapter = BasketAdapter(it)
            binding.rvBasket.layoutManager = LinearLayoutManager(requireContext())
            binding.rvBasket.adapter = adapter
        })



        toolbar?.findViewById<ImageView>(R.id.iv_delete)?.setOnClickListener {
            if (viewModel.productsInBasket.value?.size != 0) {
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle("Sepeti boşaltmak istiyor musunuz?")
                    .setNegativeButton("Evet") { dialog, view ->
                        viewModel.deleteProductsFromLocal()
                        viewModel.getProductsFromLocal()

                        dialog.dismiss()
                    }
                    .setPositiveButton("Hayır") { dialog, _ ->
                        dialog.dismiss()
                    }.create()
                dialog.show()
            }


//            viewModel.deleteProductsFromLocal()
//            viewModel.getProductsFromLocal()


        }

        // TODO: Quantity manuel olarak eklenecek.


//        val list = mutableListOf("Hi","Hello")
//        val adapter = BasketAdapter(list)
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerView.adapter = adapter
//
//
//        binding.button.setOnClickListener {
//            for (i in list.indices) {
//                list.removeAt(i)
//                adapter.notifyItemRemoved(i)
//
//            }
//
//        }


    }

}