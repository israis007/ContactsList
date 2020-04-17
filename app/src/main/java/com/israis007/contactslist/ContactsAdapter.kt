package com.israis007.contactslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(
    private val context: Context,
    private val contacts: List<Contact>,
    private val layoutId: Int
): RecyclerView.Adapter<ContactsAdapter.ContactItem>() {

    inner class ContactItem(private val contactView: View): RecyclerView.ViewHolder(contactView) {

        fun bindItems(contact: Contact, position: Int){
            val tvIndex = contactView.findViewById<TextView>(R.id.tv_index)
            val ivAvatar = contactView.findViewById<ImageView>(R.id.iv_avatar)
            val tvName = contactView.findViewById<TextView>(R.id.tv_name)

            if (position == 0 || contacts[position - 1].index != contact.index)
                tvIndex.apply {
                    visibility = View.VISIBLE
                    text = contact.index
                }
            else
                tvIndex.visibility = View.GONE

            tvName.text = contact.name

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactItem =
        ContactItem(LayoutInflater.from(context).inflate(layoutId, parent, false))

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactItem, position: Int) = holder.bindItems(contacts[position], position)
}