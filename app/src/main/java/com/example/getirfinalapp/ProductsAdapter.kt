//package com.example.getirfinalapp
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.getirfinalapp.databinding.ItemCardBinding
//
//class ProductsAdapter(val bestSellerList: List<Product>) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {
//
//    inner class ProductsViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        // val context = binding.root.context
//        fun bind(product: Product) {
//            with(binding) {
//                tvPrice.text = product.productPrice
//                tvProductName.text = product.productName
//                ivCard.setImageResource(R.drawable.basket)
//                tvAttribute.text = product.productAttribute
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
//        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        return ProductsViewHolder(binding)
//    }
//
//    override fun getItemCount() = bestSellerList.size
//
//    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
//        holder.bind(product = bestSellerList[position])
//    }
//
//
//}