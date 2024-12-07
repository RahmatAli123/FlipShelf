package com.example.flipshelf

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Registration_Screen : AppCompatActivity() {
    private var auth = FirebaseAuth.getInstance()
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_screen)


        emailEditText = findViewById(R.id.email_EditText)
        nameEditText = findViewById(R.id.name_EditText)
        passwordEditText = findViewById(R.id.password_EditText)
        registerButton = findViewById(R.id.register_Button)


        registerButton.setOnClickListener {
            val setName = nameEditText.text.toString()
            val setEmail = emailEditText.text.toString()
            val setPassword = passwordEditText.text.toString()

            if (setName.isEmpty() || setEmail.isEmpty() || setPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            else {
                auth.createUserWithEmailAndPassword(setEmail, setPassword)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
                    }

            }
            startActivity(Intent(this, LoginActivity::class.java))

        }

 }

}