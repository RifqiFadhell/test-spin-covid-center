package id.fadel.covidcenter.ui.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import id.fadel.covidcenter.R
import id.fadel.covidcenter.factory.response.ProvinsiItem

class SpinnerAdapter(private val context: Context, private val data: List<ProvinsiItem>) :
    BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val viewHolder: ViewHolder?
        val view = LayoutInflater.from(context).inflate(R.layout.general_spinner_item, parent, false)
        viewHolder = ViewHolder(view)

        viewHolder.textView.text = data[position].nama

        return view
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

    private class ViewHolder(view: View) {
        val textView: TextView = view.findViewById(R.id.textViewSpinnerItem)
    }
}