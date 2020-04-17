package com.israis007.contactslist

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.israis007.contactlist.ui.OnSelectIndexItemListener
import kotlinx.android.synthetic.main.activity_contacts.*
import java.util.*

class LeftPositionActivity: AppCompatActivity() {

    private val contacts: ArrayList<Contact> = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        initData()
        initView()
    }

    private fun initData(){
        setContentView(R.layout.activity_contacts)

        rv_contacts.layoutManager = LinearLayoutManager(this@LeftPositionActivity)
        rv_contacts.adapter = ContactsAdapter(this@LeftPositionActivity, contacts, R.layout.item_contacts_right)

        side_bar.setPosition(side_bar.POSITION_LEFT)
        side_bar.setOnSelectIndexItemListener(object: OnSelectIndexItemListener{
            override fun onSelectIndexItem(index: String) {
                for (i in 0 until contacts.size){
                    if (contacts[i].index == index){
                        (rv_contacts.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(i, 0)
                        return
                    }
                }
            }
        })
    }

    private fun initView(){
        contacts.addAll(Contact.getChineseContacts().toTypedArray())
    }
}
