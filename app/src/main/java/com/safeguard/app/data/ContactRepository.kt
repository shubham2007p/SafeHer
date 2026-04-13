package com.safeguard.app.data

import androidx.lifecycle.LiveData

class ContactRepository(private val dao: ContactDao) {
    val allContacts: LiveData<List<EmergencyContact>> = dao.getAllContacts()
    val contactCount: LiveData<Int> = dao.getCount()

    suspend fun insert(contact: EmergencyContact) = dao.insert(contact)
    suspend fun delete(contact: EmergencyContact) = dao.delete(contact)
    suspend fun getAllSync(): List<EmergencyContact> = dao.getAllContactsSync()
}
