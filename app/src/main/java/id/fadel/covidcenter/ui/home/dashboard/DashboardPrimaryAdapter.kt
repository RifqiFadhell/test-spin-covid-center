package id.fadel.covidcenter.ui.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.fadel.covidcenter.R
import id.fadel.covidcenter.factory.response.FeaturesItem
import kotlinx.android.synthetic.main.item_province.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class DashboardPrimaryAdapter(private var items: List<FeaturesItem>?, private val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_province,
                parent,
                false
            )
        )
    }

    fun updateList(list: List<FeaturesItem>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        val positive = item?.attributes?.kasusPosi
        val decimalFormat: DecimalFormat = NumberFormat.getInstance(Locale.ENGLISH) as DecimalFormat
        decimalFormat.applyPattern("#,###")
        holder.view.textProvince.text = item?.attributes?.provinsi
        holder.view.textPositive.text = decimalFormat.format(positive)
        holder.view.textDeath.text = item?.attributes?.kasusMeni.toString()
        holder.view.textHealth.text = item?.attributes?.kasusSemb.toString()

        if (positive != null) {
            if (positive > 10000) {
                holder.view.line.setBackgroundColor(ContextCompat.getColor(context, R.color.redOrange50))
            } else {
                holder.view.line.setBackgroundColor(ContextCompat.getColor(context, R.color.emerald))
            }
        }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val view = view.layout
}