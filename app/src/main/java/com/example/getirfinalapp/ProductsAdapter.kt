package com.example.getirfinalapp

import coil.load
import coil.transform.CircleCropTransformation

class ProductsAdapter(
    val listener: AddItemClickListener<ProductXX>,
    items: List<ProductXX>
) : BaseProductsAdapter<ProductXX>(listener, items) {

    override fun onBindItem(holder: BaseProductsViewHolder, item: ProductXX, position: Int) {
        with(holder.binding) {
            tvPrice.text = item.priceText
            tvProductName.text = item.name
            ivCard.load(item.imageURL) {
                placeholder(R.drawable.basket)
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            tvAttribute.text = item.attribute

            ivPlus.setOnClickListener {
                listener.onAddItemClick(item)
            }
        }
    }
}
