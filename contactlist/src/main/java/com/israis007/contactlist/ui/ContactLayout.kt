package com.israis007.contactlist.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.israis007.contactlist.R
import com.israis007.contactlist.model.ContactCardList
import com.israis007.contactlist.model.ContactModel
import com.israis007.contactlist.tools.ViewTools
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

class ContactLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var contactsList = ArrayList<ContactModel>()
    private var indexList = List(1){'A'}
    private var listener: OnContactItemListener? = null
    private var favorite = true

    constructor(context: Context, contactsList: ArrayList<ContactModel>, indexList: List<Char>): this(context){
        this.contactsList = contactsList
        Collections.sort(contactsList, ContactModelComparator())
        this.indexList = indexList
        reDraw()
    }

    init {
        val lpl = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        this@ContactLayout.orientation = VERTICAL
        this@ContactLayout.layoutParams = lpl
    }

    private fun reDraw(){
        this@ContactLayout.removeAllViews()
        val linearLayout = LinearLayout(context)
        val constraintLayout = ConstraintLayout(context)

        linearLayout.orientation = VERTICAL

        val contactCopy = CopyOnWriteArrayList<ContactModel>()
        contactCopy.addAll(contactsList)
        val contactList = ArrayList<ContactCardList>()

        /* Adding Favorite */
        if (favorite){
            val list = ArrayList<ContactModel>()
            contactCopy.forEach {
                if (it.isfavorite) {
                    list.add(it)
                    contactCopy.remove(it)
                }
            }
            contactList.add(ContactCardList('*', list))
        }

        /* Adding By Index */
        indexList.forEach {
            val list = ArrayList<ContactModel>()
            contactCopy.forEach { contactModel -> run {
                    if (contactModel.name[0] == it) {
                        list.add(contactModel)
                        contactCopy.remove(contactModel)
                    }
                }
            }
            contactList.add(ContactCardList(it, list))
        }

        /* Creating a indexer */
        val index = ArrayList<Char>()
        if (favorite)
            index.add('*')
        index.addAll(indexList)

        /* Validating if rest contacts without index */
        if (contactCopy.size > 0){
            val temp = ArrayList<ContactModel>()
            temp.addAll(contactCopy)
            contactList.add(ContactCardList('@', temp))
            index.add('@')
        }

        indexList = index.toList()

        /* Adding card */
        var first = true
        contactList.forEach {
            val view = ViewTools.getViewWithoutParent(LayoutInflater.from(context).inflate(R.layout.card_index, null, false))
            val icon = view.findViewById<ImageView>(R.id.card_index_iv_icon)
            val indexIcon = view.findViewById<AppCompatTextView>(R.id.card_index_actv_icon)
            val recycler = view.findViewById<RecyclerView>(R.id.card_index_rv_list)
            recycler.layoutManager = LinearLayoutManager(context)

            if (favorite && first){
                indexIcon.visibility = View.INVISIBLE
                icon.visibility = View.VISIBLE
                first = false
                recycler.adapter = ContactCardAdapter(context, it.listContact, listener ?: object : OnContactItemListener{
                    override fun OnContactItemClic(contactModel: ContactModel) {
                        Log.d("Adapter", "Touched -> ${contactModel.name}")
                    }
                })
            }
            indexIcon.visibility = View.VISIBLE
            icon.visibility = View.INVISIBLE
            indexIcon.text = it.charIndex.toString()
            recycler.adapter = ContactCardAdapter(context, it.listContact, listener ?: object : OnContactItemListener{
                override fun OnContactItemClic(contactModel: ContactModel) {
                    Log.d("Adapter", "Touched -> ${contactModel.name}")
                }
            })

            recycler.adapter!!.notifyDataSetChanged()
            linearLayout.addView(ViewTools.getViewWithoutParent(view))
        }

        /* Creating a scroll controller */

//        val sc = WaveSideBar(context)
//        sc.setIndexItems(indexList)
//        val lp = ConstraintLayout.LayoutParams(
//            ConstraintLayout.LayoutParams.MATCH_PARENT,
//            ConstraintLayout.LayoutParams.WRAP_CONTENT
//        )
//        lp.startToEnd = -1
//        lp.startToStart = 0
//        lp.topToTop = 0
//        lp.topToBottom = -1
//        lp.endToEnd = 0
//        lp.endToStart = -1
//        lp.bottomToBottom = 0
//        lp.bottomToTop = -1

//        sc.layoutParams = lp
//        linearLayout.layoutParams = lp

//        constraintLayout.addView(ViewTools.getViewWithoutParent(linearLayout))
//        constraintLayout.addView(ViewTools.getViewWithoutParent(sc))

        val nestedScrollView = ScrollView(context)
        nestedScrollView.addView(ViewTools.getViewWithoutParent(linearLayout))

        this@ContactLayout.addView(ViewTools.getViewWithoutParent(nestedScrollView))
    }

    fun setContacts(contactsList: ArrayList<ContactModel>){
        this.contactsList = contactsList
    }

    fun getContacts() = contactsList

    fun addContact(contactModel: ContactModel){
        this.contactsList.add(contactModel)
        reDraw()
    }

    fun removeContact(contactModel: ContactModel){
        this.contactsList.remove(contactModel)
        reDraw()
    }

    fun removeContact(index: Int){
        this.contactsList.removeAt(index)
        reDraw()
    }

    fun setContactListener(onContactItemListener: OnContactItemListener){
        this.listener = onContactItemListener
    }

    fun favoriteEnable(boolean: Boolean){
        favorite = boolean
    }

    fun setIndexes(indexList: List<Char>){
        this.indexList = indexList
    }

}