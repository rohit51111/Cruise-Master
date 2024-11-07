package com.example.cruisemaster.crewdetails.model


import java.io.Serializable


data class User(

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


   /* val shipDepartmentConnections: List<Any>,
    val shipId: String,
    val shipName: String,
    val voyageInfos: List<Any>*/

    /*val login: String,
    val id: String,
    val avatar_url: String,
    val gravatar_id: String,
    val url: String,
    val html_url: String,
    val followers_url: String,
    val following_url: String,*/

    ):Serializable

// https://api.github.com/users

// https://localhost:44307/api/Guest/getGuests

// http://192.168.0.113:5289/api/ship/GetAllShips


/* val cabinNo: Int,
 val dob: String,
 val embarkDate: String,
 val firstName: String,
 val gender: String,
 val isActive: String,
 val isCheckedIn: String,
 val isOnboard: String,
 val lastName: String,
 val myUnknownColumn: Any,
 val myUnknownColumn0: Any,
 val nationality: String,
 val personId: String,
 val reservationNumber: String,
 val sequenceNo: Int,
 val shipCode: String,
 val shipName: String,
 val title: String,
 val voyageNumber: String*/



