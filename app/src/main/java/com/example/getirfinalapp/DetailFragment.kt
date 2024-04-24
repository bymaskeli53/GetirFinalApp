package com.example.getirfinalapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.example.getirfinalapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var binding: FragmentDetailBinding by autoCleared()

    private val args: DetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = activity?.findViewById<Toolbar>(R.id.myToolbar)
        toolbar?.findViewById<LinearLayout>(R.id.toolbar_basket)?.visibility = View.INVISIBLE

        initViews()

    }

    private fun initViews() {
        binding.tvName.text = args.Product.name
        binding.tvPrice.text = args.Product.priceText

        binding.ivProduct.load(args.Product.imageURL){
            placeholder(R.drawable.basket)
            crossfade(true)
            transformations(CircleCropTransformation())
        }

    }
}