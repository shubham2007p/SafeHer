package com.safeher.app.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.safeher.app.data.AppDatabase
import com.safeher.app.data.ContactRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = ContactRepository(AppDatabase.getInstance(application).contactDao())
    val contactCount = repo.contactCount
    val allContacts = repo.allContacts
}
