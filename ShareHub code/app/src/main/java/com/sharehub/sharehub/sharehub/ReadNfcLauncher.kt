package com.sharehub.sharehub.sharehub

import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.app.Activity



class ReadNfcLauncher : ShareHubActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.read_nfc_launcher)
        val manager = this.getSystemService(Context.NFC_SERVICE) as NfcManager
        if (manager == null) {
            Toast.makeText(this, "your phone does not support nfc", Toast.LENGTH_SHORT).show()
            replaceActivity(Intent(this, Profile::class.java))
        }
        else {
            val adapter = manager.defaultAdapter
            if (adapter == null) {
                Toast.makeText(this, "your phone does not support nfc", Toast.LENGTH_SHORT).show()
                replaceActivity(Intent(this, Profile::class.java))
            }
            else if (!adapter.isEnabled) {
                Toast.makeText(this, "your phone does not have nfc enabled", Toast.LENGTH_SHORT).show()
                replaceActivity(Intent(this, Profile::class.java))
            }
            else {
                startActivity(Intent(this, ReadNfc::class.java))
            }
        }
    }
}
