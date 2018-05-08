package com.sharehub.sharehub.sharehub

import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.view.Gravity
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast

import com.sharehub.sharehub.sharehub.SaveTemplates.LoginSave
import com.sharehub.sharehub.sharehub.SaveTemplates.SavedData
import com.sharehub.sharehub.sharehub.SaveTemplates.SettingsSave

object Statics {
    var Username: String? = null
    var Settings: SettingsSave? = null
    var UserKey: String? = null
    var CurrentCallbackFromRequestNfc: ((id: String?) -> Unit)? = null;

    fun logout(context: ContextWrapper) {
        Username = null
        UserKey = null
        val loginSave = LoginSave()
        loginSave.password = null
        loginSave.username = null
        loginSave.key = null
        SavedData.Write(context, "LoginSave", loginSave)
    }

    fun comingSoon(context: Context) {
        Toast.makeText(context, "comming soon™", Toast.LENGTH_SHORT).show();
    }

    fun requestNfc(context: ContextWrapper, callback: (id: String?) -> Unit) {
        CurrentCallbackFromRequestNfc = callback
        val newIntent = Intent(context, ReadNfcLauncher::class.java)
        context.startActivity(newIntent)
    }
    fun waiterDialog(context: ContextWrapper): AlertDialog.Builder{
        val builder = AlertDialog.Builder(context)
        builder.setTitle("new name")
        val relativeLayout = RelativeLayout(context)
        relativeLayout.gravity = Gravity.CENTER
        val progressBar = ProgressBar(context)
        relativeLayout.addView(progressBar)
        builder.setView(relativeLayout)
        return builder
    }
}
