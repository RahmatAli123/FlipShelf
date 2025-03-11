package com.example.flipshelf.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.flipshelf.Model.profileModel
import com.example.flipshelf.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Nav_Header_Activity : AppCompatActivity() {
    private lateinit var userImage: ImageView
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView
    private lateinit var db: FirebaseDatabase


    @SuppressLint("MissingInflatedId", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_header)
        userImage = findViewById(R.id.userImage_ImageView)
        userName =findViewById(R.id.userName_TextView)
        userEmail = findViewById(R.id.userEmail_TextView)
        db = FirebaseDatabase.getInstance()

        getProfileData()
    }

    private fun getProfileData() {
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        db.getReference("users").child(uid.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(profileModel::class.java)
                    if (data != null) {
                        Glide.with(applicationContext).load(data.image).into(userImage)
                        userName.text = data.name
                        userEmail.text = data.email

                    } else {
                        Toast.makeText(
                            this@Nav_Header_Activity,
                            "Data Not Found${data}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }


                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

}