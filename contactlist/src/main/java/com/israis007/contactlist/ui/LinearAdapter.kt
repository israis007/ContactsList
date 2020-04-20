package com.israis007.contactlist.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.israis007.contactlist.R
import com.israis007.contactlist.model.ContactCardList
import com.israis007.contactlist.model.ContactModel
import com.israis007.contactlist.model.RelationViewIndex
import com.israis007.contactlist.tools.ViewTools
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

class LinearAdapter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var contactsList = ArrayList<ContactModel>()
    private var indexList = List(1){'A'}
    private var listener: OnContactItemListener? = null
    private var favorite = true
    private var listViews = ArrayList<RelationViewIndex>()

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
        this@LinearAdapter.orientation = VERTICAL
        this@LinearAdapter.layoutParams = lpl
    }

    private fun reDraw(){
        this@LinearAdapter.removeAllViews()
        val linearLayout = LinearLayout(context)
        linearLayout.id = View.generateViewId()
        val relativeLayout = RelativeLayout(context)
        relativeLayout.id = View.generateViewId()

        linearLayout.orientation = VERTICAL

        listViews = ArrayList()

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

            if (it.listContact.size == 0){
                val ind = ArrayList<Char>()
                ind.addAll(indexList)
                ind.remove(it.charIndex)
                indexList = ind.toList()
            } else {

                val view = ViewTools.getViewWithoutParent(
                    LayoutInflater.from(context).inflate(R.layout.card_index, null, false)
                )
                val consl_root = view.findViewById<ConstraintLayout>(R.id.card_index_conslay_root)
                val icon = view.findViewById<ImageView>(R.id.card_index_iv_icon)
                val indexIcon = view.findViewById<AppCompatTextView>(R.id.card_index_actv_icon)
                val recycler = view.findViewById<RecyclerView>(R.id.card_index_rv_list)

                val lpt = recycler.layoutParams
                val ltemp = LinearLayout(context)
                ltemp.orientation = VERTICAL
                ltemp.layoutParams = lpt

                it.listContact.forEach { contactModel ->
                    val contactView =
                        LayoutInflater.from(context).inflate(R.layout.card_contact, null, false)
                    val cl_root =
                        contactView.findViewById<ConstraintLayout>(R.id.card_cont_constlayout_root)
                    val iv_avatar = contactView.findViewById<ImageView>(R.id.card_cont_iv_avatar)
                    val aptv_name =
                        contactView.findViewById<AppCompatTextView>(R.id.card_cont_aptv_name)
                    val aptv_info =
                        contactView.findViewById<AppCompatTextView>(R.id.card_cont_aptv_info)

                    Glide.with(context).load(contactModel.iconURL ?: contactModel.iconDrawable)
                        .fitCenter()
                        .circleCrop().error(R.drawable.ic_avatar).placeholder(R.drawable.ic_avatar)
                        .into(iv_avatar)

                    aptv_name.text = contactModel.name
                    aptv_name.setOnClickListener {
                        listener?.OnContactItemClic(contactModel)
                    }
                    iv_avatar.setOnClickListener {
                        val alert = AlertDialog.Builder(context)
                        alert.setPositiveButton(R.string.close_dialog) { dialog, _ ->
                            dialog.dismiss()
                        }
                        val iv = ImageView(context)
                        val lp = ViewGroup.LayoutParams(
                            500,
                            500
                        )
                        iv.layoutParams = lp
                        Glide.with(context).load(contactModel.iconURL ?: contactModel.iconDrawable)
                            .fitCenter().circleCrop().error(R.drawable.ic_avatar)
                            .placeholder(R.drawable.ic_avatar).into(iv)
                        alert.setTitle(contactModel.name)
                        alert.setView(iv)
                        val dial = alert.create()
                        dial.show()
                    }

                    if (contactModel.info_view != null) {
                        val lpc = aptv_info.layoutParams as ConstraintLayout.LayoutParams
                        cl_root.removeView(aptv_info)
                        val viewn = contactModel.info_view!!
                        viewn.layoutParams = lpc
                        cl_root.addView(viewn)
                        viewn.setOnClickListener {
                            listener?.OnContactItemClic(contactModel)
                        }
                    } else {
                        aptv_info.text = contactModel.info
                        aptv_info.setOnClickListener {
                            listener?.OnContactItemClic(contactModel)
                        }
                    }
                    cl_root.setOnClickListener {
                        listener?.OnContactItemClic(contactModel)
                    }
                    ltemp.addView(contactView)
                }

                consl_root.removeView(recycler)
                consl_root.addView(ltemp)

                if (favorite && first) {
                    indexIcon.visibility = View.INVISIBLE
                    icon.visibility = View.VISIBLE
                    first = false
                    recycler.adapter = ContactCardAdapter(
                        context,
                        it.listContact,
                        listener ?: object : OnContactItemListener {
                            override fun OnContactItemClic(contactModel: ContactModel) {
                                Log.d("Adapter", "Touched -> ${contactModel.name}")
                            }
                        })
                } else {
                    indexIcon.visibility = View.VISIBLE
                    icon.visibility = View.INVISIBLE
                    indexIcon.text = it.charIndex.toString()
                    recycler.adapter = ContactCardAdapter(
                        context,
                        it.listContact,
                        listener ?: object : OnContactItemListener {
                            override fun OnContactItemClic(contactModel: ContactModel) {
                                Log.d("Adapter", "Touched -> ${contactModel.name}")
                            }
                        })
                }

                listViews.add(RelationViewIndex(it.charIndex.toString(), ViewTools.getViewWithoutParent(view)))
                linearLayout.addView(ViewTools.getViewWithoutParent(view))
            }
        }

        /* Creating a scroll controller */

        val indexer_lay = LayoutInflater.from(context).inflate(R.layout.indexer_layout, null ,false)
        val waveSideBar = indexer_lay.findViewById<WaveSideBar>(R.id.side_bar)
        waveSideBar.setIndexItems(indexList)
        val lp = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        lp.addRule(RelativeLayout.CENTER_IN_PARENT)

        val nestedScrollView = ScrollView(context)
        nestedScrollView.addView(ViewTools.getViewWithoutParent(linearLayout))

        nestedScrollView.layoutParams = lp

        relativeLayout.addView(nestedScrollView)

        waveSideBar.setOnSelectIndexItemListener(object: OnSelectIndexItemListener{
            override fun onSelectIndexItem(index: String) {
                Log.d("Linear", "Selected Index $index")
                for (i in 0 until listViews.size){
                    if (listViews[i].index == index){
                        nestedScrollView.scrollTo(0, listViews[i].view.y.toInt())
                        return
                    }
                }
            }
        })

        relativeLayout.addView(ViewTools.getViewWithoutParent(waveSideBar))

        this@LinearAdapter.addView(ViewTools.getViewWithoutParent(relativeLayout))
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