package com.mydigipay.challenge.presentation.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mydigipay.challenge.R
import com.mydigipay.challenge.utils.network.RxNetworkReceiver
import lightStatusBar
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var subs: Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lightStatusBar()
        supportActionBar?.hide()
        checkConnection()
    }

    private fun checkConnection() {
        subs = RxNetworkReceiver.stream(applicationContext)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Boolean> {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {
                   val message = "Network is not active switch on wifi or mobile data"
                    Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                }
                override fun onNext(aBoolean: Boolean) {
                    val pinging = ProcessBuilder()
                    if (aBoolean) {
                        try {
                            // ping to ip for check internet
                            Observable.just(
                                pinging.command("/system/bin/ping", "-c1", "8.8.8.8")
                                    .redirectErrorStream(true).start()
                            )
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : Observer<Process> {
                                    override fun onCompleted() {}
                                    override fun onError(e: Throwable) {
                                        val message = "Network is active but no internet"
                                        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                                        e.printStackTrace()
                                    }

                                    override fun onNext(process: Process) {
                                       val message = "Internet connection is active"
                                        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                                    }
                                })
                        } catch (e: IOException) {
                            val message = "Network is active but internet is not working"
                            Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                        }
                    } else {
                        val message = "Network is active but internet is not working"
                        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

}
