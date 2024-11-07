package com.example.cruisemaster.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cruisemaster.localdb.tables.Guest

@Dao
    interface GuestInfoDao {
        @Insert
        suspend fun insertGuestInfo(guestInfo: Guest)

        @Query("SELECT * FROM guest_info WHERE personId = :personId")
        suspend fun getGuestInfoById(personId: String): Guest?

        @Query("DELETE FROM guest_info")
        suspend fun deleteAllGuests()

        @Query("SELECT * FROM guest_info")
        suspend fun getAllGuests(): List<Guest>

        @Query("SELECT COUNT(*) FROM guest_info")
        suspend fun getCount(): Int

    }
