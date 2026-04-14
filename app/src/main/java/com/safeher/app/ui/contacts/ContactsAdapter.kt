package com.safeher.app.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.safeher.app.data.EmergencyContact
import com.safeher.app.databinding.ItemContactBinding

class ContactsAdapter(
    private val onDelete: (EmergencyContact) -> Unit
) : ListAdapter<EmergencyContact, ContactsAdapter.ViewHolder>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<EmergencyContact>() {
            override fun areItemsTheSame(a: EmergencyContact, b: EmergencyContact) = a.id == b.id
            override fun areContentsTheSame(a: EmergencyContact, b: EmergencyContact) = a == b
        }
    }

    inner class ViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: EmergencyContact) {
            binding.tvName.text = contact.name
            binding.tvPhone.text = contact.phone
            binding.btnDelete.setOnClickListener { onDelete(contact) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
