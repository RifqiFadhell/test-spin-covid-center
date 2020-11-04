package id.fadel.covidcenter.ui.home.dashboard

import id.fadel.covidcenter.factory.response.DataCovidResponse
import id.fadel.covidcenter.factory.response.ProvinceResponse

class DashboardContract {

    interface View {

        fun getResponse(response: DataCovidResponse)

        fun getResponseProvince(response: ProvinceResponse)

        fun showDialog()

        fun hideDialog()

        fun getMessage(message: String)
    }

    interface Presenter {

        fun getDataCovid()

        fun getDataProvince()
    }
}