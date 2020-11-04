package id.fadel.covidcenter.ui.home.dashboard

import id.fadel.covidcenter.factory.ApiFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DashboardPresenter(private val view: DashboardContract.View): DashboardContract.Presenter {

    private var disposable: Disposable? = null

    private val apiServe by lazy {
        ApiFactory.create()
    }

    private val apiServeSecond by lazy {
        ApiFactory.createSecond()
    }

    override fun getDataCovid() {
        view.showDialog()
        disposable = apiServe.getDataCovid()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                view.getResponse(result)
            },
                { error ->
                    view.getMessage(error.message.toString())
                    view.hideDialog()
                }
            )
    }

    override fun getDataProvince() {
        view.showDialog()
        disposable = apiServeSecond.getDataProvince()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                view.getResponseProvince(result)
            },
                { error ->
                    view.getMessage(error.message.toString())
                    view.hideDialog()
                }
            )
    }
}