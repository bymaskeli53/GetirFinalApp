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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getirfinalapp.databinding.FragmentBasketBinding

class BasketFragment : Fragment(R.layout.fragment_basket) {

    private var binding: FragmentBasketBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasketBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val toolbar = activity?.findViewById<Toolbar>(R.id.myToolbar)
        val toolbarText =toolbar?.findViewById<TextView>(R.id.tv_toolbar_title)
        toolbarText?.text = "Sepetim" // TODO: String multilanguage yapılacak

        toolbar?.findViewById<ImageView>(R.id.iv_delete)?.visibility = View.VISIBLE

        toolbar?.findViewById<LinearLayout>(R.id.toolbar_basket)?.visibility = View.GONE

        toolbar?.findViewById<ImageView>(R.id.iv_cancel)?.visibility = View.VISIBLE

        toolbar?.findViewById<ImageView>(R.id.iv_cancel)?.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

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