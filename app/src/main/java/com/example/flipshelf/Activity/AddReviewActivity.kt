package com.example.flipshelf.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.flipshelf.R
import com.google.android.material.textfield.TextInputEditText

class AddReviewActivity : AppCompatActivity() {
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_review)
        val nameInput = findViewById<TextInputEditText>(R.id.et_name)
        val experienceInput = findViewById<TextInputEditText>(R.id.et_experience)
        val seekBar = findViewById<SeekBar>(R.id.seekbar_rating)
        val minStarText = findViewById<TextView>(R.id.tv_min_star)
        val maxStarText = findViewById<TextView>(R.id.tv_max_star)
        val submitButton = findViewById<Button>(R.id.btn_submit)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("DefaultLocale")
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val starRating = p1 / 10.0
                minStarText.text = String.format("%.1f", starRating)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        submitButton.setOnClickListener {
            val name = nameInput.text.toString()
            val experience = experienceInput.text.toString()
            val starRating = seekBar.progress / 10.0

            // Validation
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (experience.isEmpty()) {
                Toast.makeText(this, "Please describe your experience", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Display the review details
            Toast.makeText(
                this,
                "Name: $name\nExperience: $experience\nRating: ${String.format("%.1f", starRating)}",
                Toast.LENGTH_LONG
            ).show()

        }
    }
}