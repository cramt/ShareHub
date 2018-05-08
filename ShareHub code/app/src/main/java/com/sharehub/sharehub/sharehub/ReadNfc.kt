package com.sharehub.sharehub.sharehub

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.IsoDep
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import android.nfc.tech.Ndef
import android.nfc.tech.NfcA
import android.nfc.tech.NfcB
import android.nfc.tech.NfcF
import android.nfc.tech.NfcV
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.sharehub.sharehub.sharehub.Statics.CurrentCallbackFromRequestNfc

class ReadNfc : Activity() {
    private val techList = arrayOf(arrayOf(NfcA::class.java.name, NfcB::class.java.name, NfcF::class.java.name, NfcV::class.java.name, IsoDep::class.java.name, MifareClassic::class.java.name, MifareUltralight::class.java.name, Ndef::class.java.name))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.read_nfc)
    }

    override fun onResume() {
        super.onResume()
        // creating pending intent:
        val pendingIntent = PendingIntent.getActivity(this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        // creating intent receiver for NFC events:
        val filter = IntentFilter()
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED)
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED)
        // enabling foreground dispatch for getting intent from NFC event:
        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, arrayOf(filter), this.techList)
        val backButton = findViewById<Button>(R.id.read_nfc_back_button)
        backButton.setOnClickListener { view ->
            run {
                if (Statics.CurrentCallbackFromRequestNfc != null) {
                    Statics.CurrentCallbackFromRequestNfc!!(null)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // disabling foreground dispatch:
        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcAdapter.disableForegroundDispatch(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onNewIntent(intent: Intent) {
        if (intent.action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            Statics.CurrentCallbackFromRequestNfc?.let { it(ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID))) }
        }
        else {
            if (Statics.CurrentCallbackFromRequestNfc != null) {
                Statics.CurrentCallbackFromRequestNfc!!(null)
            }
        }
    }

    private fun ByteArrayToHexString(inarray: ByteArray): String {
        val out = StringBuilder()
        for (anInarray in inarray) {
            out.append(anInarray.toInt() and 0xff)
            out.append(",")
        }
        return out.toString().substring(0, out.length - 1)
    }
}