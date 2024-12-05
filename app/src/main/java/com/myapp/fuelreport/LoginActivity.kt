package com.myapp.fuelreport

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditTxt: EditText
    private lateinit var passEditText: EditText
    private lateinit var loginBtn: TextView
    private lateinit var forgetPass: TextView
    private lateinit var goRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        emailEditTxt = findViewById(R.id.emailEditText)
        passEditText = findViewById(R.id.passEditText)
        loginBtn = findViewById(R.id.Login_btn)
        forgetPass = findViewById(R.id.forgetPass)
        goRegister = findViewById(R.id.goRegister)


        loginBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Finish SplashScreenActivity to prevent returning to it
            finish()
        }


    }
}