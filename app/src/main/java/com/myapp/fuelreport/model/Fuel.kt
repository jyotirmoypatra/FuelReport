package com.myapp.fuelreport.model

data class Fuel(
    val fuelTypeId: Long,
    val name: String,
    val unit: String,
    val pricePerUnit: Double,
    val nozzels: List<Nozzle>?,
)
