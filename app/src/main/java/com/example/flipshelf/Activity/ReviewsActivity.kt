package com.example.flipshelf.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flipshelf.R
import com.google.android.material.button.MaterialButton

class ReviewsActivity : AppCompatActivity() {
    lateinit var addReview: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)
        addReview = findViewById(R.id.btn_add_review)
        addReview.setOnClickListener {
            val intent = Intent(this, AddReviewActivity::class.java)
            startActivity(intent)
        }

    }
}