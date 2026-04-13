package com.safeguard.app.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Query("SELECT * FROM emergency_contacts ORDER BY name ASC")
    fun getAllContacts(): LiveData<List<EmergencyContact>>

    @Query("SELECT * FROM emergency_contacts ORDER BY name ASC")
    suspend fun getAllContactsSync(): List<EmergencyContact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: EmergencyContact)

    @Delete
    suspend fun delete(contact: EmergencyContact)

    @Query("SELECT COUNT(*) FROM emergency_contacts")
    fun getCount(): LiveData<Int>
}
