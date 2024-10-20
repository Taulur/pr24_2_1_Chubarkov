package com.example.fragmvar3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fragmvar3.ui.theme.FragmVar3Theme
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    lateinit var login: EditText
    lateinit var pass: EditText
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        login=findViewById(R.id.login)
        pass=findViewById(R.id.pass)


    }
    fun reg(view: View) {

        if (!login.text.isNullOrEmpty() && !pass.text.isNullOrEmpty()) {
            pref = getPreferences(MODE_PRIVATE)
            val ed = pref.edit()
            ed.putString("login", login.text.toString())
            ed.putString("password", pass.text.toString())
            ed.apply()
            val snackbar = Snackbar.make(view, "Вы успешно прошли регистрацию!", Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(android.graphics.Color.RED)
            snackbar.show()

        } else {
            val snackbar = Snackbar.make(view, "Вы ввели данные неверно!", Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(android.graphics.Color.RED)
            snackbar.show()
        }
    }

    fun entry(view: View) {
        pref=getPreferences(MODE_PRIVATE)
        if(!login.text.toString().isNullOrEmpty() && !pass.text.toString().isNullOrEmpty())
        {
            if (login.text.toString()==(pref.getString("login",""))&&pass.text.toString()==(pref.getString("password","")))
            {

                val intent = Intent(this, DataActivity::class.java)
                startActivity(intent)
            }
            else {

                var snackbar = Snackbar.make(findViewById(android.R.id.content),"Неверный лоигн или пароль", Snackbar.LENGTH_LONG)
                snackbar.setActionTextColor(android.graphics.Color.RED)
                snackbar.show()
            }

        }
        else
        {
            var snackbar = Snackbar.make(findViewById(android.R.id.content),"Вы ввели данные неверно!", Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(android.graphics.Color.RED)
            snackbar.show()
        }
    }

}