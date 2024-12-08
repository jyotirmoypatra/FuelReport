package com.myapp.fuelreport.Util

import com.myapp.fuelreport.model.Fuel
import com.myapp.fuelreport.model.UserValidationResponse
import com.myapp.fuelreport.model.getFuelAndTransactiobApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Path

interface UserService {

    @GET("fuel/login.php")
    fun validateUser(
        @Header("Username") username: String,
        @Header("Password") password: String
    ): Call<UserValidationResponse>


    @GET("fuel/{organizationId}/fetch.php")
    fun getFuelTypesAndDetails(@Path("organizationId") organizationId: Int):
            Call<getFuelAndTransactiobApiResponse>



//    // Example POST request
//    @POST("api/login")
//    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}