package com.israis007.contactslist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.israis007.contactlist.ui.OnSelectIndexItemListener
import kotlinx.android.synthetic.main.activity_contacts.*
import java.util.*

class CustomIndexActivity : AppCompatActivity() {

    private val contacts = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData()
        initView()
    }

    private fun initData(){
        contacts.addAll(Contact.getJapaneseContacts())
    }

    private fun initView(){
        setContentView(R.layout.activity_contacts)

        rv_contacts.layoutManager = LinearLayoutManager(this@CustomIndexActivity)
        rv_contacts.adapter = ContactsAdapter(this@CustomIndexActivity, contacts, R.layout.item_contacts)

        side_bar.setIndexItems("あ", "か", "さ", "た", "な", "は", "ま", "や", "ら", "わ")
        side_bar.setOnSelectIndexItemListener(object : OnSelectIndexItemListener{
            override fun onSelectIndexItem(index: String) {
                for (i in 0 until contacts.size){
                    if (contacts[i].index == index) {
                        (rv_contacts.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(i, 0)
                        return
                    }
                }
            }
        })
    }
}
