package com.loopmoth.loobuilder

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*
import kotlinx.android.synthetic.main.row_item.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Cart", Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show()
        }

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
        cardObudowa.setOnClickListener{
            val intent = Intent(this, ProductList::class.java)
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
