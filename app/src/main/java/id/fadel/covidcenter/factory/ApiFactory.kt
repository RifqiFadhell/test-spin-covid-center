package id.fadel.covidcenter.factory

import id.fadel.covidcenter.factory.response.DataCovidResponse
import id.fadel.covidcenter.factory.response.ProvinceResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


interface ApiFactory {

    /*this is endpoint*/
    @GET("VS6HdKS0VfIhv8Ct/arcgis/rest/services/COVID19_Indonesia_per_Provinsi/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json")
    fun getDataCovid(): Observable<DataCovidResponse>

    @GET("api/daerahindonesia/provinsi")
    fun getDataProvince(): Observable<ProvinceResponse>

    companion object {

        fun create(): ApiFactory {
            /*this is for add timeout*/
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            /*this is for connect api*/
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("https://services5.arcgis.com/")
                .build()

            return retrofit.create(ApiFactory::class.java)
        }

        /*this is for connect api*/
        fun createSecond(): ApiFactory {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://dev.farizdotid.com/")
                .build()

            return retrofit.create(ApiFactory::class.java)
        }


    }
}

