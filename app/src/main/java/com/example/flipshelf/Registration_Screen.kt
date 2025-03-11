package com.example.flipshelf

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flipshelf.Activity.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Registration_Screen : AppCompatActivity() {
    private var auth = FirebaseAuth.getInstance()
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginTextView: TextView
    private lateinit var db: FirebaseDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_screen)


        emailEditText = findViewById(R.id.email_EditText)
        nameEditText = findViewById(R.id.name_EditText)
        passwordEditText = findViewById(R.id.password_EditText)
        registerButton = findViewById(R.id.register_Button)
        loginTextView = findViewById(R.id.login_TextView)
        db = FirebaseDatabase.getInstance()


        registerButton.setOnClickListener {
            val setName = nameEditText.text.toString()
            val setEmail = emailEditText.text.toString()
            val setPassword = passwordEditText.text.toString()

            if (validateInput(setName, setEmail, setPassword)) {
                auth.createUserWithEmailAndPassword(setEmail, setPassword)
                    .addOnSuccessListener {
                        val data=HashMap<String,Any>()
                        data["image"]= ""
                        data["name"]=nameEditText.text.toString()
                        data["email"]=setEmail
                        db.getReference("users").child(auth.currentUser!!.uid).setValue(data)
                        finish()
                        emailEditText.text.clear()
                        passwordEditText.text.clear()
                        nameEditText.text.clear()
                        Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@Registration_Screen, LoginActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
                    }
            }

        }

        loginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java)).apply {
                finish()
            }
        }


    }

    private fun validateInput(name: String, email: String, password: String): Boolean {
        if (name.isEmpty()) {
            showToast("Name cannot be empty")
            return false
        }
        if (email.isEmpty()) {
            showToast("Email cannot be empty")
            return false
        }
        if (!isValidEmail(email)) {
            showToast("Please enter a valid email")
            return false
        }
        if (password.isEmpty()) {
            showToast("Password cannot be empty")
            return false
        }
        if (password.length < 8) {
            showToast("Password must be at least 8 characters long")
            return false
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}