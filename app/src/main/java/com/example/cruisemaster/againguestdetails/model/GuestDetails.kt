package com.example.cruisemaster.againguestdetails.model


data class GuestDetails(



    val personId: String,
    var isCheckedIn: String,
    val cabinNo: Int,
    val checkedinTerminal: String,
    val dbarkDate: String,
    val dob: String,
    val embarkDate: String,
    val firstName: String,
    val gender: String,
    val isOnboard: String,
    val lastName: String,
    val nationality: String,
    val reservationNumber: String,
    val sequenceNo: Int,
    val voyageNumber: String
)