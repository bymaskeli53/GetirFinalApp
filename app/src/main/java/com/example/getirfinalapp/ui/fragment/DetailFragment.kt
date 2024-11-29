package com.example.getirfinalapp.ui.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.example.getirfinalapp.R
import com.example.getirfinalapp.util.autoCleared
import com.example.getirfinalapp.databinding.FragmentDetailBinding
import com.example.getirfinalapp.util.invisible
import com.example.getirfinalapp.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var binding: FragmentDetailBinding by autoCleared()

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_in)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = activity?.findViewById<Toolbar>(R.id.myToolbar)

        toolbar?.findViewById<ImageView>(R.id.iv_cancel)?.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        toolbar?.findViewById<TextView>(R.id.tv_toolbar_title)?.text =
            getString(R.string.product_detail)

        setupToolbar()

        initViews()

    }

    private fun initViews() {
        binding.tvName.text = args.Product.name
        binding.tvPrice.text = args.Product.priceText


        binding.ivProduct.load(args.Product.imageURL ?: args.Product.squareThumbnailURL){
            placeholder(R.drawable.basket)
            crossfade(true)
            transformations(CircleCropTransformation())
        }

    }

    private fun setupToolbar() {
        val toolbar = activity?.findViewById<Toolbar>(R.id.myToolbar)
        toolbar?.findViewById<LinearLayout>(R.id.toolbar_basket)?.invisible()
        toolbar?.findViewById<ImageView>(R.id.iv_cancel)?.show()
    }
}