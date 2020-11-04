package id.fadel.covidcenter.preference

import android.content.Context
import com.iamhabib.easy_preference.EasyPreference

class DataSaved(val context: Context?) {

    /*this is for data local saved in shared preference*/

    companion object {
        private const val TAG_SPLASH = "splash"
        private const val TAG_LOGIN = "login"
    }

    fun saveSplash(splash: String, context: Context?) {
        EasyPreference.with(context, TAG_SPLASH).addString(TAG_SPLASH, splash).save()
    }

    fun getSplash(context: Context?): String {
        return EasyPreference.with(context, TAG_SPLASH).getString(TAG_SPLASH, "")
    }

    fun saveLogin(login: String, context: Context?) {
        EasyPreference.with(context, TAG_LOGIN).addString(TAG_LOGIN, login).save()
    }

    fun getLogin(context: Context?): String {
        return EasyPreference.with(context, TAG_LOGIN).getString(TAG_LOGIN, "")
    }

    fun removeAll(context: Context?) {
        EasyPreference.with(context, TAG_SPLASH).clearAll().save()
        EasyPreference.with(context, TAG_LOGIN).clearAll().save()
    }
}