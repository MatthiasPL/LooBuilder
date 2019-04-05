package com.loopmoth.loobuilder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        cardObudowa.setOnClickListener{
            Toast.makeText(this@MainMenuActivity, "Obudowa", Toast.LENGTH_SHORT).show()
        }
    }
}
