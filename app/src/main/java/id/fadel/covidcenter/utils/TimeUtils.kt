package id.fadel.covidcenter.utils

import android.os.Build
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object TimeUtils {

    fun getFormattedDate(): String {

        val dates: String

        dates = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val answer: String = current.format(formatter)
            formatDate(answer, "EEE, dd MMMM yyyy")

        } else {

            val date = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val answer: String = formatter.format(date)
            formatDate(answer, "EEE, dd MMMM yyyy")
        }

        return dates
    }

    private fun formatDate(date: String, format: String): String {
        var formattedDate = ""
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        try {
            val parseDate = sdf.parse(date)
            formattedDate = SimpleDateFormat(format).format(parseDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formattedDate
    }
}