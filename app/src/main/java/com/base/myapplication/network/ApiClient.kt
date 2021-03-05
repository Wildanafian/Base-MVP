package com.base.myapplication.network

import android.app.Application
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.base.myapplication.BuildConfig
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ApiClient : Application() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiEndPoint.VERSION)
        .client(getOkHttpClient1()!!)
//        .client(getUnsafeOkHttpClient()!!)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val services: ApiInterface = retrofit.create(ApiInterface::class.java)

    private fun getOkHttpClient1(): OkHttpClient? {
        val okHttpBuilder = OkHttpClient.Builder()
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        dispatcher.maxRequestsPerHost = 1
        okHttpBuilder.connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .dispatcher(dispatcher)

        okHttpBuilder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val ongoing = chain.request().newBuilder()
            ongoing.addHeader("Content-Type", "application/json")
//            ongoing.addHeader("key", "")
            chain.proceed(ongoing.build())
        })

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            okHttpBuilder.addInterceptor(interceptor)
        }

        return okHttpBuilder.build()
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient? {
        return try { // Create a trust manager that does not validate certificate chains
            val trustAllCerts =
                arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(
                sslSocketFactory,
                (trustAllCerts[0] as X509TrustManager)
            )
            builder.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val ongoing = chain.request().newBuilder()
                    ongoing.addHeader("Content-Type", "application/json")
                    ongoing.addHeader("key", "")
                    return chain.proceed(ongoing.build())
                }
            })
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}