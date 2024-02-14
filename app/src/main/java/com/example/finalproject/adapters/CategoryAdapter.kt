package com.example.finalproject.adapters

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.CategoryRvItemBinding
import com.example.finalproject.model.category.Category

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val binding: CategoryRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.apply {
                val borderDrawable = ShapeDrawable(RectShape())
                borderDrawable.paint.strokeWidth = (4).toFloat()
                borderDrawable.paint.style = android.graphics.Paint.Style.STROKE
                itemView.background = borderDrawable

                val resourceId = itemView.context.resources.getIdentifier(category.src, "drawable", itemView.context.packageName)
                imgCategory.setImageResource(resourceId)
                tvCategory.text = category.name
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        return CategoryViewHolder(
            CategoryRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        val category = differ.currentList[position]
        holder.bind(category)

        holder.itemView.setOnClickListener {
            onClick?.invoke(category)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick: ((Category) -> Unit)? = null
}