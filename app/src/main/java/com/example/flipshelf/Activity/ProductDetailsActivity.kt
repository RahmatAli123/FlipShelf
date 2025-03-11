package com.example.flipshelf.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.flipshelf.R

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var productDetailsImageView: ImageView
    private lateinit var productDetailsName: TextView
    private lateinit var productDetailsPrice: TextView
    private lateinit var productDetailsDescription: TextView
    private lateinit var viewAllReviews: TextView
    private lateinit var arrowImageView: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        productDetailsImageView = findViewById(R.id.productDetailsImage_ImageView)
        productDetailsName=findViewById(R.id.productDetailsName_TextView)
        productDetailsPrice=findViewById(R.id.productDetailsPrice_TextView)
        productDetailsDescription=findViewById(R.id.productDetailsDescription_TextView)
        viewAllReviews=findViewById(R.id.viewAllReviews_TextView)
        arrowImageView=findViewById(R.id.arrow_ImageView)
        viewAllReviews.setOnClickListener {
            val intent= Intent(this, ReviewsActivity::class.java)
            startActivity(intent)
        }
        arrowImageView.setOnClickListener {
            onBackPressed()
        }



      val productImage=intent.getStringExtra("productImage")?.replace("[","")
        val productName=intent.getStringExtra("productName")
        val productPrice=intent.getStringExtra("productPrice")
        val productDescription=intent.getStringExtra("productDescription")
//        Glide.with(this).load(productImage).error(R.drawable.ic_launcher_background).into(productDetailsImageView)
        Glide.with(this).load(productImage).into(productDetailsImageView)
        productDetailsName.text=productName
        productDetailsPrice.text=productPrice
        productDetailsDescription.text=productDescription

    }
}