package com.example.cruisemaster.localdb.tables

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "guest_info")
data class Guest(

    val title: String,
    @PrimaryKey
    val personId: String,
    val isActive: String,
    val isOnBoard: String,
    val reservationNumber: String,
    val embarkDate: String,
    val debarkDate: String,
    val firstName: String,
    val lastName: String,
    val isCheckedIn: String,
    val guestOrCrew: String,
    val shipId: String,
    val shipName: String,
    val sequenceNo: Int,
    val voyageNumber: String,
    val voyageStartDate: String,
    val voyageEndDate: String,
    val isVoyageActive: String,
    val portName: String,
    val dob: String,
    val gender: String,
    val nationality: String,
    val cabinNo: Int,
    val checkedinTerminal: String,
    val profileImage: String
)