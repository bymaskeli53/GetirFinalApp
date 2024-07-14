package com.example.getirfinalapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getirfinalapp.databinding.ItemCardBinding

abstract class BaseProductsAdapter<T>(private val listener: AddItemClickListener<T>, private val items: List<T>) :
    RecyclerView.Adapter<BaseProductsAdapter<T>.BaseProductsViewHolder>() {

    abstract fun onBindItem(holder: BaseProductsViewHolder, item: T, position: Int)

    inner class BaseProductsViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            onBindItem(this, item, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseProductsViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseProductsViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseProductsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    interface AddItemClickListener<T> {
        fun onAddItemClick(item: T)
    }
}
