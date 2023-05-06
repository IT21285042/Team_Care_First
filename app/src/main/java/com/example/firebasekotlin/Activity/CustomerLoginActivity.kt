package com.example.firebasekotlin.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebasekotlin.DashboardActivity
import com.example.firebasekotlin.R
import com.google.firebase.auth.FirebaseAuth

class CustomerLoginActivity : AppCompatActivity() {

    private lateinit var userEmailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var fireBaseAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_login)

        userEmailEditText = findViewById(R.id.userEmailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        fireBaseAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            val emailText = userEmailEditText.text.toString()
            val passwordText = passwordEditText.text.toString()

            if (emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            } else {
                fireBaseAuth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}