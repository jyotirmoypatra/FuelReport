package com.myapp.fuelreport.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.myapp.fuelreport.R
import com.myapp.fuelreport.Util.ApiService
import com.myapp.fuelreport.model.UserValidationResponse
import com.myapp.fuelreport.model.getFuelAndTransactiobApiResponse
import retrofit2.Call

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditTxt: EditText
    private lateinit var passEditText: EditText
    private lateinit var loginBtn: TextView
    private lateinit var forgetPass: TextView
    private lateinit var goRegister: TextView
    private lateinit var progressBarLogin: FrameLayout

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
        progressBarLogin = findViewById(R.id.progressBarLogin)


        loginBtn.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            // Finish SplashScreenActivity to prevent returning to it
//            finish()
            loginApiCall(emailEditTxt.text.toString(),passEditText.text.toString())
        }

        geAutologinDetailsFromLocalStorage()


    }

    private fun geAutologinDetailsFromLocalStorage() {
        val sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)

        val username = sharedPreferences.getString("username", null)
        val password = sharedPreferences.getString("password", null)
        val orgId = sharedPreferences.getInt("orgId", -1)  // Default to -1 if not found

        if (username != null && password != null && orgId != -1) {
            loginApiCall(username,password)
        }
    }


    private fun loginApiCall(user:String,pass:String) {

        progressBarLogin.visibility = View.VISIBLE

        val userService = ApiService.getUserService()

        userService.validateUser(user,pass)
            .enqueue(object : retrofit2.Callback<UserValidationResponse> {
                override fun onResponse(
                    call: Call<UserValidationResponse>,
                    response: retrofit2.Response<UserValidationResponse>
                ) {
                    when {
                        response.isSuccessful -> {
                            // Handle 200 response
                            val data = response.body()
                            Log.d("api-res",""+data)
                            data?.let {
                                if (it.validUser) {

                                    // User is valid, save the user data
                                    saveUserDataToPreferences(user, pass, it.orgId)
                                    println("User is valid. Org ID: ${it.orgId}")
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    // Finish SplashScreenActivity to prevent returning to it
                                    finish()
                                } else {
                                    // Show alert for invalid user
                                    showAlert("Login Failed", "Invalid credentials. Please try again.")
                                }
                            }
                        }
                        response.code() == 404 -> {
                            // Handle 404 error specifically
                            showAlert("Login Failed", "User not found. Please check your credentials.")
                        }
                        else -> {
                            // Handle other HTTP errors
                            showAlert(
                                "Login Failed",
                                "Error: ${response.code()}. Please try again later."
                            )
                        }


                    }
                    progressBarLogin.visibility = View.GONE
                }

                override fun onFailure(call: Call<UserValidationResponse>, t: Throwable) {
                    // Handle the error
                    progressBarLogin.visibility = View.GONE
                }
            })
    }

    // Function to show an alert dialog
    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog when the user clicks OK
            }
            .setCancelable(false)
            .show()
    }
    private fun saveUserDataToPreferences(username: String, password: String, orgId: Int?) {
        val sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Save username and password as strings
        editor.putString("username", username)
        editor.putString("password", password)

        // Save orgId as an integer
        editor.putInt("orgId", orgId ?: 0)

        editor.apply()
    }

}