package com.example.flipshelf.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flipshelf.Model.sellerModel
import com.example.flipshelf.R
import com.example.flipshelf.Activity.productDetailsInterface


class RecyclerAdapter(private val itemsList: ArrayList<sellerModel>, private val listener: productDetailsInterface): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name_TextView)
        val priceTextView: TextView = itemView.findViewById(R.id.price_TextView)
        val imageView: ImageView = itemView.findViewById(R.id.image_ImageView)
//        val heartImageView: ImageView = itemView.findViewById(R.id.heart_ImageView)
        val DsTextView: TextView = itemView.findViewById(R.id.description_TextView)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
     return itemsList.size

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentItem = itemsList[position]
        holder.nameTextView.text = currentItem.name
        holder.priceTextView.text = currentItem.price
        holder.DsTextView.text = currentItem.description
        Glide.with(holder.itemView.context).load(currentItem.image).into(holder.imageView)
//        Glide.with(holder.itemView.context).load(currentItem.heart).into(holder.heartImageView)

        holder.itemView.setOnClickListener {
            listener.productDetails(currentItem)
        }


    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(filteredList: List<sellerModel>) {
        itemsList.clear()
        itemsList.addAll(filteredList)
        notifyDataSetChanged()
    }


}