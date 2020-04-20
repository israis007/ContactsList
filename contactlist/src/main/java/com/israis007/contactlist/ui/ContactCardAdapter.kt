package com.israis007.contactlist.ui

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.israis007.contactlist.R
import com.israis007.contactlist.model.ContactModel

class ContactCardAdapter(
    private val context: Context,
    private val contactList: ArrayList<ContactModel>,
    private val onContactItemListener: OnContactItemListener
) : RecyclerView.Adapter<ContactCardAdapter.ContactItem>() {

    inner class ContactItem(private val contactView: View) : RecyclerView.ViewHolder(contactView) {
        fun bindItems(contactModel: ContactModel) {
            val cl_root = contactView.findViewById<ConstraintLayout>(R.id.card_cont_constlayout_root)
            val iv_avatar = contactView.findViewById<ImageView>(R.id.card_cont_iv_avatar)
            val aptv_name = contactView.findViewById<AppCompatTextView>(R.id.card_cont_aptv_name)
            val aptv_info = contactView.findViewById<AppCompatTextView>(R.id.card_cont_aptv_info)

            Glide.with(context).load(contactModel.iconURL ?: contactModel.iconDrawable).fitCenter()
                .circleCrop().error(R.drawable.ic_avatar).placeholder(R.drawable.ic_avatar)
                .into(iv_avatar)

            aptv_name.text = contactModel.name
            aptv_name.setOnClickListener {
                onContactItemListener.OnContactItemClic(contactModel)
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
                    onContactItemListener.OnContactItemClic(contactModel)
                }
            } else {
                aptv_info.text = contactModel.info
                aptv_info.setOnClickListener {
                    onContactItemListener.OnContactItemClic(contactModel)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactItem =
        ContactItem(LayoutInflater.from(context).inflate(R.layout.card_contact, parent, false))


    override fun getItemCount(): Int =
        contactList.size

    override fun onBindViewHolder(holder: ContactItem, position: Int) =
        holder.bindItems(contactList[position])

}