package com.example.cruisemaster.againcrewdetails.model


data class CrewDetails(

    val cabinNo: Int,
    val deptId: Int,
    val firstName: String,
    val isCheckedIn: String,
    val isOnboard: String,
    val lastName: String,
    val personId: String,
    val safetyNo: Int,
    val shipId: String,
    val shipName: String,
    val signOffDate: String,
    val signOnDate: String,
    val title: String
)