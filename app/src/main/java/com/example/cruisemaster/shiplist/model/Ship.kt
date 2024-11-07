package com.example.cruisemaster.shiplist.model
data class Ship(
    val shipId: String,
    val shipName: String,
    val shipDepartmentConnections: List<Any>,
    val voyageInfos: List<Any>
)
