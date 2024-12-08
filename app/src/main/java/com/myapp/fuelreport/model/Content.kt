package com.myapp.fuelreport.model

data class Content(
    val orgId: Int,
    val fuelTypes: List<Fuel>,
    val transactionTypes: List<TransactionType>
)
