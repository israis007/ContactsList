package com.israis007.contactslist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.israis007.contactlist.model.ContactModel
import com.israis007.contactlist.ui.ContactLayout
import com.israis007.contactlist.ui.LinearAdapter
import com.israis007.contactlist.ui.OnContactItemListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lista = arrayListOf(
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'Ñ',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z'
        )

        val clay = LinearAdapter(this@MainActivity, DummyContacts.getContactList(), lista)
//            clay.setContacts(DummyContacts.getContactList())
        clay.setContactListener(object: OnContactItemListener{
            override fun OnContactItemClic(contactModel: ContactModel) {
                Log.d("MainActivity", "Contacto seleccionado -> ${contactModel.name}")
            }
        })

        linear_lay.addView(clay)

    }

    fun openRightPositionSample(view: View?) {
        val intent = Intent(this, RightPositionActivity::class.java)
        startActivity(intent)
    }

    fun openLeftPositionSample(view: View?) {
        val intent = Intent(this, LeftPositionActivity::class.java)
        startActivity(intent)
    }

    fun openCustomIndexSample(view: View?) {
        val intent = Intent(this, CustomIndexActivity::class.java)
        startActivity(intent)
    }
}
