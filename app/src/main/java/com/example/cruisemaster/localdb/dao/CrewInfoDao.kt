package com.example.cruisemaster.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cruisemaster.localdb.tables.Crew

@Dao
interface CrewInfoDao {
    @Insert
    suspend fun insertCrewInfo(crewInfo: Crew)

    @Query("SELECT * FROM crew_info WHERE personId = :personId")
    suspend fun getCrewInfoById(personId: String): Crew?

    @Query("SELECT COUNT(*) FROM crew_info")
    suspend fun getCount(): Int

    @Query("DELETE FROM crew_info")
    suspend fun deleteAllCrew()

    @Query("SELECT * FROM crew_info")
    suspend fun getAllCrew(): List<Crew>
}