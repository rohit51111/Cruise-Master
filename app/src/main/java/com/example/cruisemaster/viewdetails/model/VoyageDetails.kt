package com.example.cruisemaster.viewdetails.model

data class VoyageDetails(

    val voyageNumber: String,
    val shipId : String,
    val voyageStartDate : String,
    val voyageEndDate : String,
    val isVoyageActive : String,
    val portName : String,
    val guestInfos : List<Any>,
    val ship : List<Any>
)

