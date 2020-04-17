package com.israis007.contactslist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.israis007.contactlist.ui.OnSelectIndexItemListener
import com.israis007.contactlist.ui.WaveSideBar
import kotlinx.android.synthetic.main.activity_contacts.*
import java.util.*

class RightPositionActivity: AppCompatActivity() {

    private val contacts: ArrayList<Contact> = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initView()
    }

    private fun initView() {
        setContentView(R.layout.activity_contacts)

        rv_contacts.layoutManager = LinearLayoutManager(this@RightPositionActivity)
        rv_contacts.adapter = ContactsAdapter(this@RightPositionActivity, contacts, R.layout.item_contacts)

        side_bar.setOnSelectIndexItemListener(object : OnSelectIndexItemListener{
            override fun onSelectIndexItem(index: String) {
                for (i in 0 until contacts.size) {
                    if (contacts[i].index == index) {
                        (rv_contacts.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(i, 0)
                        return
                    }
                }
            }
        })
    }

    private fun initData() {
        contacts.addAll(Contact.getEnglishContacts().toTypedArray())
    }

}
