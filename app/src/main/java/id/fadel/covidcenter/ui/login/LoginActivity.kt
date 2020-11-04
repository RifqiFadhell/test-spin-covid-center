package id.fadel.covidcenter.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.fadel.covidcenter.R
import id.fadel.covidcenter.preference.DataSaved
import id.fadel.covidcenter.ui.home.HomeActivity
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity() {

    private var dataSaved: DataSaved? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        init()
        initData()
        initListener()
    }

    private fun init() {
        dataSaved = DataSaved(this)
    }

    private fun initData() {

    }

    private fun initListener() {
        buttonLogin.setOnClickListener { validateLogin() }
    }

    private fun validateLogin() {
        val id = "admin"
        val password = "admin"

        val valueId = editId.editableText.toString()
        val valuePassword = editPassword.editableText.toString()

        if (valueId != id && valuePassword != password) {
            textInputId.error = "Id tidak sama"
            textInputPassword.error = "Password tidak sama"
        } else if (valueId != id) {
            textInputId.error = "Id tidak sama"
            textInputPassword.error = null
        } else if (valuePassword != password) {
            textInputId.error = null
            textInputPassword.error = "Password tidak sama"
        } else {
            goToHome()
        }
    }

    private fun goToHome() {
        dataSaved?.saveLogin("login", this)
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}