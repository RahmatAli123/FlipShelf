package com.example.flipshelf.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flipshelf.R
import com.example.flipshelf.Registration_Screen
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passEditText: EditText
    private lateinit var loginBtn:Button
    private lateinit var auth: FirebaseAuth
    private lateinit var singUpTextView: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth=FirebaseAuth.getInstance()
        emailEditText=findViewById(R.id.emailLogin_EditText)
        passEditText=findViewById(R.id.passwordLogin_EditText)
        loginBtn=findViewById(R.id.login_Button)
        singUpTextView=findViewById(R.id.signUp_TextView)

        loginBtn.setOnClickListener {
            val email=emailEditText.text.toString()
            val password=passEditText.text.toString()
                if (validateInput(email, password)) {
                    auth.signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show().apply {
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                        }

                }

            }



        singUpTextView.setOnClickListener {
            startActivity(Intent(this, Registration_Screen::class.java)).apply {
                finish()
            }
        }




    }
    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            showToast("Email cannot be empty")
            return false
        }
        if (!isValidEmail(email)) {
            showToast("Please enter a valid email address")
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
        val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        return email.matches(emailPattern.toRegex())
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}