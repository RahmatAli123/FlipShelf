package com.example.flipshelf

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passEditText: EditText
    private lateinit var loginBtn:Button
    private lateinit var auth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth=FirebaseAuth.getInstance()
        emailEditText=findViewById(R.id.emailLogin_EditText)
        passEditText=findViewById(R.id.passwordLogin_EditText)
        loginBtn=findViewById(R.id.login_Button)

        loginBtn.setOnClickListener {
            val email=emailEditText.text.toString()
            val password=passEditText.text.toString()
            if (email.isEmpty() && password.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()

            }
            else{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            startActivity(Intent(this,MainActivity::class.java))
        }



    }
}