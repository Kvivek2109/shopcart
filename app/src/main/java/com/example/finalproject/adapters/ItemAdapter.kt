package com.example.finalproject.adapters

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.ProductRvItemBinding
import com.example.finalproject.model.item.Item

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ProductRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.apply {
                val borderDrawable = ShapeDrawable(RectShape())
                borderDrawable.paint.strokeWidth = (4).toFloat()
                borderDrawable.paint.style = android.graphics.Paint.Style.STROKE
                itemView.background = borderDrawable

                Glide.with(itemView).load(item.src).into(itemImg)
                itemName.text = item.name
                val price   = item.price
                itemPrice.text = "$ ${String.format("%.2f", price)}"
            }

        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemAdapter.ItemViewHolder {
        return ItemViewHolder(
            ProductRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick: ((Item) -> Unit)? = null
}