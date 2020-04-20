package com.israis007.contactlist.ui

import com.israis007.contactlist.model.ContactModel

class ContactModelComparator: Comparator<ContactModel> {
    override fun compare(contact1: ContactModel?, contact2: ContactModel?): Int {
        return contact1!!.name.compareTo(contact2!!.name)
    }
}