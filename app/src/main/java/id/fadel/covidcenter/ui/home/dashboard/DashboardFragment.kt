package id.fadel.covidcenter.ui.home.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import id.fadel.covidcenter.R
import id.fadel.covidcenter.factory.response.DataCovidResponse
import id.fadel.covidcenter.factory.response.FeaturesItem
import id.fadel.covidcenter.factory.response.ProvinceResponse
import id.fadel.covidcenter.factory.response.ProvinsiItem
import id.fadel.covidcenter.utils.TimeUtils
import id.fadel.covidcenter.utils.hideKeyboard
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.dashboard_fragment.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.Comparator
import kotlin.collections.ArrayList

class DashboardFragment : Fragment(), DashboardContract.View {

    private var presenter: DashboardPresenter? = null
    private var dialog: AlertDialog? = null
    private var adapter: DashboardPrimaryAdapter? = null
    private var listDataProvince = ArrayList<FeaturesItem>()
    private var dataProvince = ArrayList<ProvinsiItem>()
    private var isShowFilter: Boolean = false
    private var isAtoZ: Boolean = false
    private var isLtoH: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dashboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initData()
        initListener()
    }

    private fun init() {
        initProgressDialog()
        presenter = DashboardPresenter(this)
    }

    private fun initData() {
        presenter?.getDataCovid()
        presenter?.getDataProvince()
        showDialog()
        textTitleDate.text = TimeUtils.getFormattedDate()
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
        buttonFilter.setOnClickListener {
            if (isShowFilter) {
                layoutFilter.visibility = View.GONE
                isShowFilter = false
            } else {
                layoutFilter.visibility = View.VISIBLE
                isShowFilter = true
            }
        }

        buttonSortName.setOnClickListener { sortName() }

        buttonSortNumber.setOnClickListener { sortNumber() }

        editSearch.afterTextChangeEvents()
            .skip(1)
            .debounce(700, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                filter(it.editable.toString().capitalize())
                hideKeyboard()
            }

        swipeRefresh.setOnRefreshListener {
            presenter?.getDataCovid()
            swipeRefresh.isRefreshing = false
        }

        textSpinner.setOnClickListener {
            textSpinner.visibility = View.GONE
            spinnerProvince.visibility = View.VISIBLE
            showSelectSpinner()
            spinnerProvince.performClick()
        }
    }

    /*this is for filtering words*/
    private fun filter(text: String) {
        val temp: MutableList<FeaturesItem> = ArrayList()
        for (x in listDataProvince) {
            if (x.attributes?.provinsi!!.contains(text)) {
                temp.add(x)
            }
        }
        adapter?.updateList(temp)
    }

    override fun getResponseProvince(response: ProvinceResponse) {
        dataProvince.addAll(response.provinsi as ArrayList)
        val adapter = context?.let { SpinnerAdapter(it, dataProvince) }
        spinnerProvince.adapter = adapter
    }

    private fun showSelectSpinner() {
        spinnerProvince.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    filter(dataProvince[position].nama.toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
    }

    override fun getResponse(response: DataCovidResponse) {
        listDataProvince.clear()
        listDataProvince.addAll(response.features as ArrayList<FeaturesItem>)
        totalData(response.features)
        setList(0)
    }

    private fun setList(code: Int) {

        when (code) {
            1 -> {
                listDataProvince.sortWith(Comparator { lhs, rhs ->
                    lhs?.attributes?.provinsi!!.compareTo(rhs?.attributes?.provinsi.toString())
                })
            }
            2 -> {
                listDataProvince.sortWith(Comparator { lhs, rhs ->
                    rhs?.attributes?.provinsi!!.compareTo(lhs?.attributes?.provinsi.toString())
                })
            }
            3 -> {
                listDataProvince.sortWith(Comparator { lhs, rhs ->
                    lhs?.attributes?.kasusPosi!!.compareTo(rhs?.attributes?.kasusPosi!!)
                })
            }
            4 -> {
                listDataProvince.sortWith(Comparator { lhs, rhs ->
                    rhs?.attributes?.kasusPosi!!.compareTo(lhs?.attributes?.kasusPosi!!)
                })
            }
        }

        adapter = context?.let { DashboardPrimaryAdapter(listDataProvince, it) }
        adapter?.notifyDataSetChanged()
        listProvince.adapter = adapter
        listProvince.layoutManager = LinearLayoutManager(context)
        listProvince.setHasFixedSize(true)
        listProvince.itemAnimator = DefaultItemAnimator()
        hideDialog()
    }

    /*this is for count data*/
    private fun totalData(data: ArrayList<FeaturesItem>) {
        var totalPositive = 0
        var totalDeath = 0
        var totalHealth = 0

        for (x in 0 until data.size) {
            if (data[x].attributes != null) {
                totalPositive += data[x].attributes!!.kasusPosi
                totalDeath += data[x].attributes!!.kasusMeni
                totalHealth += data[x].attributes!!.kasusSemb
            }
        }
        val decimalFormat: DecimalFormat = NumberFormat.getInstance(Locale.ENGLISH) as DecimalFormat
        decimalFormat.applyPattern("#,###")

        textDeath.text = decimalFormat.format(totalDeath)
        textPositive.text = decimalFormat.format(totalPositive)
        textHealth.text = decimalFormat.format(totalHealth)
    }

    private fun sortName() {
        if (isAtoZ) {
            setList(2)
            isAtoZ = false
            buttonSortName.setCompoundDrawablesWithIntrinsicBounds(null, null,
                context?.let { ContextCompat.getDrawable(it, R.drawable.ic_sort_name_ztoa) }, null
            )
        } else {
            setList(1)
            isAtoZ = true
            buttonSortName.setCompoundDrawablesWithIntrinsicBounds(null, null,
                context?.let { ContextCompat.getDrawable(it, R.drawable.ic_sort_name_atoz) }, null
            )
        }
    }

    private fun sortNumber() {
        if (isLtoH) {
            setList(3)
            isLtoH = false
            buttonSortNumber.setCompoundDrawablesWithIntrinsicBounds(null, null,
                context?.let { ContextCompat.getDrawable(it, R.drawable.ic_sort_number_low) }, null
            )
        } else {
            setList(4)
            isLtoH = true
            buttonSortNumber.setCompoundDrawablesWithIntrinsicBounds(null, null,
                context?.let { ContextCompat.getDrawable(it, R.drawable.ic_sort_number_high) }, null
            )
        }
    }

    override fun showDialog() {
        dialog?.show()
    }

    override fun hideDialog() {
        dialog?.hide()
    }

    override fun getMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun initProgressDialog() {
        val progressDialog = context?.let {
            AlertDialog.Builder(it)
                .setView(R.layout.progress_dialog)
                .setCancelable(false)
        }

        dialog = progressDialog?.create()
    }
}