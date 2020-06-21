package com.mydigipay.challenge.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.mydigipay.challenge.R

class WaitDialog(context: Context) : Dialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_progress)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        window!!.setDimAmount(0.08f)
        this.window!!.setBackgroundDrawableResource(R.color.colorBgWaitingDialog)
    }


}