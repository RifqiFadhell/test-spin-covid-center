package id.fadel.covidcenter.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import id.fadel.covidcenter.R
import id.fadel.covidcenter.preference.DataSaved
import id.fadel.covidcenter.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private var dataSaved: DataSaved? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        init()
        initData()
        initListener()
    }

    private fun init() {
        dataSaved = DataSaved(this)
    }

    private fun initData() {
        if (dataSaved?.getSplash(this).isNullOrEmpty()) {
            Handler(Looper.getMainLooper()).postDelayed({
                goToLogin()
            }, 3000)
        } else {
            goToLogin()
        }
    }

    private fun initListener() {

    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}