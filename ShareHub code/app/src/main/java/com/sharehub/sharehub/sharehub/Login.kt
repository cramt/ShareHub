package com.sharehub.sharehub.sharehub

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import com.sharehub.sharehub.sharehub.SaveTemplates.LoginSave
import com.sharehub.sharehub.sharehub.SaveTemplates.SavedData

import org.json.JSONException
import org.json.JSONObject

import cz.msebera.android.httpclient.Header
import org.w3c.dom.Text

class Login : ShareHubActivityLayoutBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.login)
        super.onCreate(savedInstanceState)
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val intent = intent
        loginButton.setOnClickListener { view -> login(usernameInput.text.toString(), passwordInput.text.toString()) }
        if (intent.hasExtra("type")) {
            when (intent.getStringExtra("type")) {
                "autologin" -> if (intent.hasExtra("username") && intent.hasExtra("password")) {
                    login(intent.getStringExtra("username"), intent.getStringExtra("password"))

                }
            }
        }
    }

    private fun login(username: String, password: String) {
        val client = AsyncHttpClient()
        val params = RequestParams()
        params.add("username", username)
        params.add("password", password)
        client.post(Constants.UserGateway.Check, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>?, response: JSONObject?) {
                if (!response!!.has("success")) {
                    Toast.makeText(this@Login, "unexpected http response", Toast.LENGTH_LONG).show()
                    return
                }
                try {
                    if (response.getBoolean("success")) {
                        val loginSave = LoginSave()
                        loginSave.key = response.getString("cookieKey")
                        if (Statics.Settings!!.storeUserLoginData) {
                            loginSave.username = username
                            loginSave.password = password
                        }
                        else {
                            loginSave.password = null
                            loginSave.username = null
                        }
                        SavedData.Write(this@Login, "LoginSave", loginSave)
                        Statics.Username = username
                        Statics.UserKey = loginSave.key
                        replaceActivity(Intent(this@Login, ProfileLauncher::class.java))
                    }
                    else {
                        Toast.makeText(this@Login, response.getString("message"), Toast.LENGTH_LONG).show()
                    }
                }
                catch (e: JSONException) {
                    Toast.makeText(this@Login, "unexpected json error: " + e.message, Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, res: String?, t: Throwable) {
                Toast.makeText(this@Login, "an error occurred when sending the http request: " + res!!, Toast.LENGTH_LONG).show()
            }
        })
    }
}