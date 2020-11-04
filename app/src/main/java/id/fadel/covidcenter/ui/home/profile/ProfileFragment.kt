package id.fadel.covidcenter.ui.home.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.fadel.covidcenter.R
import id.fadel.covidcenter.preference.DataSaved
import id.fadel.covidcenter.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {

    private var dataSaved: DataSaved? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initData()
        initListener()
    }

    private fun init() {
        dataSaved = DataSaved(context)
    }

    private fun initData() {

    }

    private fun initListener() {
        buttonLogout.setOnClickListener { goToLogin() }
    }

    private fun goToLogin() {
        dataSaved?.removeAll(context)
        val intent = Intent(context, SplashActivity::class.java)
        activity?.startActivity(intent)
        activity?.finish()
    }
}