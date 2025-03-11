package com.example.flipshelf.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flipshelf.Model.productModel
import com.example.flipshelf.R

class WalletAdapter(private val productList: ArrayList<productModel>): RecyclerView.Adapter<WalletAdapter.ProductViewHolder>() {
    private var filteredList: List<productModel> = productList
    inner class ProductViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val productImage = item.findViewById<ImageView>(R.id.product_ImageView)
        val productItemName=item.findViewById<TextView>(R.id.productName_TextView)
        val productPrice=item.findViewById<TextView>(R.id.productPrice_TextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.product_layout, parent, false)
        return ProductViewHolder(item)

    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = filteredList[position]
        holder.productItemName.text = item.title
        holder.productPrice.text = item.price
        Glide.with(holder.itemView.context).load(item.image).into(holder.productImage)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            productList
        } else {
            productList.filter {
                it.title?.contains(query, ignoreCase = true) == true
            }
        }
        notifyDataSetChanged()
    }

}