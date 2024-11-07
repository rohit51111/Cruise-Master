package com.example.cruisemaster.localdb.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crew_info")
data class Crew(
    val title: String,
    @PrimaryKey
    val personId: String,
    val deptId: Int,
    val safetyNo: Int,
    val signOnDate: String,
    val signOffDate: String,
    val firstName: String,
    val lastName: String,
    val isOnBoard: String,
    val isCheckedIn: String,
    val checkedInTerminal: String,
    val guestOrCrew: String,
    val shipId: String,
    val shipName: String,
    val department: String,
    val position: String,
    val dob: String,
    val gender: String,
    val nationality: String,
    val cabinNo: Int,
    val profileImage: String
)