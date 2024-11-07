package com.example.cruisemaster.crewdetails.model


data class UserResponse(
    val crewCount: Int,
    val checkedInCount: Int,
    val onboardedCount: Int,
    val crewInfo : ArrayList<User>

)
