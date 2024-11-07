package com.example.cruisemaster.reservationno.model

import com.google.gson.annotations.SerializedName

data class UserResponse3(


    val cabinNo: Int,
    val count: Int,
    val guests: ArrayList<User3>,
    val reservationNumber: String
)
