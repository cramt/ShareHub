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

class Register : ShareHubActivityLayoutBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.register)
        super.onCreate(savedInstanceState)
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput1 = findViewById<EditText>(R.id.passwordInput1)
        val passwordInput2 = findViewById<EditText>(R.id.passwordInput2)
        val loginButton = findViewById<Button>(R.id.loginButton)
        run {
            val editorActionListener = { textView: TextView, i: Int, keyEvent: KeyEvent ->
                if (i == 5) {
                    register(usernameInput.text.toString(), passwordInput1.text.toString(), passwordInput2.text.toString())
                }
                false
            }
            usernameInput.setOnEditorActionListener(editorActionListener)
            passwordInput1.setOnEditorActionListener(editorActionListener)
            loginButton.setOnClickListener { view -> register(usernameInput.text.toString(), passwordInput1.text.toString(), passwordInput2.text.toString()) }
        }
    }

    private fun register(username: String, password1: String, password2: String) {
        if (password1 != password2) {
            Toast.makeText(this, "passwords doesn't match", Toast.LENGTH_SHORT).show()
            return
        }
        val client = AsyncHttpClient()
        val params = RequestParams()
        params.add("username", username)
        params.add("password", password1)
        client.post(Constants.UserGateway.New, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>?, response: JSONObject?) {
                if (!response!!.has("success")) {
                    Toast.makeText(this@Register, "unexpected http response", Toast.LENGTH_LONG).show()
                    return
                }
                try {
                    if (response.getBoolean("success")) {
                        val loginSave = LoginSave()
                        loginSave.key = response.getString("cookieKey")
                        if (Statics.Settings!!.storeUserLoginData) {
                            loginSave.username = username
                            loginSave.password = password1
                        } else {
                            loginSave.password = null
                            loginSave.username = null
                        }
                        SavedData.Write(this@Register, "LoginSave", loginSave)
                        Statics.Username = username
                        Statics.UserKey = loginSave.key
                        replaceActivity(Intent(this@Register, ProfileLauncher::class.java))
                    } else {
                        Toast.makeText(this@Register, response.getString("message"), Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    Toast.makeText(this@Register, "unexpected json error: " + e.message, Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, res: String?, t: Throwable) {
                Toast.makeText(this@Register, "an error occurred when sending the http request: " + res!!, Toast.LENGTH_LONG).show()
            }
        })
    }
}
