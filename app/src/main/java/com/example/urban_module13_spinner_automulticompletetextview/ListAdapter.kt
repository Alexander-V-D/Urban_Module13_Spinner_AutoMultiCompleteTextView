package com.example.urban_module13_spinner_automulticompletetextview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ListAdapter(context: Context, persons: List<Person>):
ArrayAdapter<Person>(context, R.layout.list_item, persons){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val person = getItem(position)
        if (view == null) {
            view =
                LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }
        val itemNameTV = view?.findViewById<TextView>(R.id.itemNameTV)
        val itemSurnameTV = view?.findViewById<TextView>(R.id.itemSurnameTV)
        val itemAgeTV = view?.findViewById<TextView>(R.id.itemAgeTV)
        val itemRoleTV = view?.findViewById<TextView>(R.id.itemRoleTV)

        itemNameTV?.text = person?.name
        itemSurnameTV?.text = person?.surname
        itemAgeTV?.text = person?.age.toString()
        itemRoleTV?.text = person?.role

        return view!!
    }
}