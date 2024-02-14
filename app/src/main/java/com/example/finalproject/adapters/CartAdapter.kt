package com.example.finalproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.CartRvItemBinding
import com.example.finalproject.model.cart.CartItem
import com.example.finalproject.model.item.ItemList

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>()  {

    inner class CartViewHolder(val binding: CartRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) {
            val item = ItemList.getProductById(cartItem.itemId)
            if (item != null) {
                Glide.with(itemView).load(item.src).into(binding.cartItemImage)
                binding.cartItemName.text = item.name
                val price = cartItem.quantity*item.price
                binding.cartItemPrice.text = "$ ${String.format("%.2f", price)}"
                binding.cartItemQuantity.text = cartItem.quantity.toString()
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return ( oldItem.itemId == newItem.itemId && oldItem.user == newItem.user)
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
        ): CartAdapter.CartViewHolder {
            return CartViewHolder(
                CartRvItemBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
        }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        val cartItem = differ.currentList[position]
        holder.bind(cartItem)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(cartItem)
        }

        holder.binding.cartItemIncrease.setOnClickListener {
            onIncreaseClick?.invoke(cartItem)
        }

        holder.binding.cartItemDecrease.setOnClickListener {
            onDecreaseClick?.invoke(cartItem)
        }

        holder.binding.cartItemDelete.setOnClickListener {
            onDeleteClick?.invoke(cartItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClick: ((CartItem) -> Unit)? = null
    var onIncreaseClick: ((CartItem) -> Unit)? = null
    var onDecreaseClick: ((CartItem) -> Unit)? = null
    var onDeleteClick: ((CartItem) -> Unit)? = null
}