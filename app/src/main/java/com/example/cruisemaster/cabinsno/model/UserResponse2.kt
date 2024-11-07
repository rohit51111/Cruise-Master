package com.example.cruisemaster.cabinsno.model

import com.google.gson.annotations.SerializedName

data class UserResponse2(
    /*@SerializedName("data")
    val guests : ArrayList<User2>*/


    val cabinNo: Int,
    val count: Int,
    val guests: ArrayList<User2>,
    val reservationNumber: Any
)
