package com.safeher.app.ui.contacts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.safeher.app.data.AppDatabase
import com.safeher.app.data.ContactRepository
import com.safeher.app.data.EmergencyContact
import kotlinx.coroutines.launch

class ContactsViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = ContactRepository(AppDatabase.getInstance(application).contactDao())
    val contacts = repo.allContacts

    fun addContact(name: String, phone: String) = viewModelScope.launch {
        repo.insert(EmergencyContact(name = name, phone = phone))
    }

    fun deleteContact(contact: EmergencyContact) = viewModelScope.launch {
        repo.delete(contact)
    }
}
