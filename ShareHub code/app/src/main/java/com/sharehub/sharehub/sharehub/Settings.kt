package com.sharehub.sharehub.sharehub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast

import com.sharehub.sharehub.sharehub.SaveTemplates.SavedData
import com.sharehub.sharehub.sharehub.SaveTemplates.SettingsSave

class Settings : ShareHubActivityBase() {

    private var settings: SettingsSave? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        val storeUserLoginData_checkBox = findViewById<CheckBox>(R.id.StoreUserLoginData_check_box)
        val saveButton = findViewById<Button>(R.id.save_button)
        val logoutButton = findViewById<Button>(R.id.logout_button)
        settings = SavedData.Read<SettingsSave>(this, "SettingsSave")
        if (settings == null) {
            settings = SettingsSave()
        }
        storeUserLoginData_checkBox.isChecked = settings!!.storeUserLoginData
        saveButton.setOnClickListener { view ->
            settings!!.storeUserLoginData = storeUserLoginData_checkBox.isChecked
            SavedData.Write<SettingsSave>(this, "SettingsSave", settings!!)
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
        }
        logoutButton.setOnClickListener { view ->
            Statics.logout(this)
            replaceActivity(Intent(this, Login::class.java))
        }
    }
}
