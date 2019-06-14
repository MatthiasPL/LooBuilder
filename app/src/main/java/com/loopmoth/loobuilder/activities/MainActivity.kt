package com.loopmoth.loobuilder.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import com.loopmoth.loobuilder.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.app.Activity
import android.graphics.Color


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()

        fabCart.setOnClickListener { view ->
            Snackbar.make(view, "Cart", Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show()
        }

        fabInfo.setOnClickListener{
            val intent = Intent(this, HelpActivity::class.java)
            // To pass any data to next activity
            //intent.putExtra("keyIdentifier", value)
            // start your next activity
            startActivity(intent)
        }

        //obrazek przy włączeniu aplikacji, który jest zamiszczony w MainActivity na samej górze
        Picasso
            .get()
            .load("https://previews.123rf.com/images/elen1/elen11611/elen1161100188/68680219-dark-pcb-board-integrated-circuit-pc-parts-motherboard-chip-processor-texture-background.jpg")
            .fit()
            .into(imageView)

        initMenuList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initMenuList(){

        //TODO: oprogramowanie przycisków
        cardObudowa.setOnClickListener{
            val intent = Intent(this, ProductListActivity::class.java)
            // To pass any data to next activity
            //intent.putExtra("keyIdentifier", value)
            // start your next activity
            startActivity(intent)
        }
        cardChlodzenie.setOnClickListener{

        }
        cardDysk.setOnClickListener{

        }
        cardKartaGraficzna.setOnClickListener{

        }
        cardPlytaGlowna.setOnClickListener{

        }
        cardProcesor.setOnClickListener{

        }
        cardRAM.setOnClickListener{

        }
    }

    private fun printSnack(view: View, text: String){
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
            .setAction(text, null)
            .show()
    }
}
