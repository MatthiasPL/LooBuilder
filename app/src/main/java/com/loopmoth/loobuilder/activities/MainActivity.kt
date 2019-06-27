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
import android.opengl.Visibility
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.loopmoth.loobuilder.classes.KoszykTest
import com.loopmoth.loobuilder.classes.User
import com.loopmoth.loobuilder.interfaces.ComputerPart
import kotlinx.android.synthetic.main.activity_product_list.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var koszykTest: KoszykTest? = null

    val filename = "userID.conf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        hideIcons()

        FirebaseApp.initializeApp(this)
        database = FirebaseDatabase.getInstance().reference
    }

    override fun onResume() {
        super.onResume()

        fabCart.setOnClickListener { view ->
            /*Snackbar.make(view, "Cart", Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show()*/
            val intent = Intent(this, Cart::class.java)
            // To pass any data to next activity
            //intent.putExtra("keyIdentifier", value)
            // start your next activity
            startActivity(intent)
        }

        fabInfo.setOnClickListener{
            val intent = Intent(this, CheckerActivity::class.java)
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

        readUserID()

        initCart()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        when (item.itemId) {
            R.id.action_help ->{
                val intent = Intent(this, HelpActivity::class.java)
                startActivity(intent)}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initMenuList(){

        //TODO: oprogramowanie przycisków
        cardObudowa.setOnClickListener{
            val intent = Intent(this, ProductListActivity::class.java)
            // To pass any data to next activity
            intent.putExtra("ComponentName", "Obudowa")
            // start your next activity
            startActivity(intent)
        }
        cardDyskHDD.setOnClickListener{
            val intent = Intent(this, ProductListActivity::class.java)
            // To pass any data to next activity
            intent.putExtra("ComponentName", "Dysk_HDD")
            // start your next activity
            startActivity(intent)
        }
        cardDyskSSD.setOnClickListener{
            val intent = Intent(this, ProductListActivity::class.java)
            // To pass any data to next activity
            intent.putExtra("ComponentName", "Dysk_SSD")
            // start your next activity
            startActivity(intent)
        }
        cardKartaGraficzna.setOnClickListener{
            val intent = Intent(this, ProductListActivity::class.java)
            // To pass any data to next activity
            intent.putExtra("ComponentName", "Karta_graficzna")
            // start your next activity
            startActivity(intent)
        }
        cardPlytaGlowna.setOnClickListener{
            val intent = Intent(this, ProductListActivity::class.java)
            // To pass any data to next activity
            intent.putExtra("ComponentName", "Plyta_glowna")
            // start your next activity
            startActivity(intent)
        }
        cardProcesor.setOnClickListener{
            val intent = Intent(this, ProductListActivity::class.java)
            // To pass any data to next activity
            intent.putExtra("ComponentName", "Procesor")
            // start your next activity
            startActivity(intent)
        }
        cardRAM.setOnClickListener{
            val intent = Intent(this, ProductListActivity::class.java)
            // To pass any data to next activity
            intent.putExtra("ComponentName", "RAM")
            // start your next activity
            startActivity(intent)
        }
        cardZasilacz.setOnClickListener {
            val intent = Intent(this, ProductListActivity::class.java)
            // To pass any data to next activity
            intent.putExtra("ComponentName", "Zasilacz")
            // start your next activity
            startActivity(intent)
        }
    }

    private fun printSnack(view: View, text: String){
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
            .setAction(text, null)
            .show()
    }

    fun readUserID(){
        val filename = "userID.conf"
        //tworzy się nowy plik, gdzie przechowywany jest ID użytkownika tworzony przez FireB

        if(fileList().contains(filename)) {
            //jeżeli plik istnieje
            readID(filename)
        }
        else{
            createFileWithID(filename)
        }
    }

    fun createFileWithID(filename: String){
        //tworzy plik
        try {
            val database = FirebaseDatabase.getInstance().reference
            val newUser = database.child("users").push()
            val user = User(newUser.key)
            newUser.setValue(user)

            val file = OutputStreamWriter(openFileOutput(filename, Activity.MODE_PRIVATE))
            file.write (newUser.key)
            file.flush ()
            file.close ()

            //Toast.makeText(this, "stworzono", Toast.LENGTH_LONG).show()
        } catch (e : IOException) {
        }
    }

    /*fun readID(filename: String){
        //czyta ID
        try {
            val file = InputStreamReader(openFileInput(filename))
            val br = BufferedReader(file)
            var line = br.readLine()
            val all = StringBuilder()
            while (line != null) {
                all.append(line + "\n")
                line = br.readLine()
            }
            br.close()
            file.close()
            //tvID.text=all
        }
        catch (e: IOException) {
        }
    }*/

    fun readID(filename: String):String{
        //czyta ID
        try {
            val file = InputStreamReader(openFileInput(filename))
            val br = BufferedReader(file)
            var line = br.readLine()
            val all = StringBuilder()
            while (line != null) {
                all.append(line)
                line = br.readLine()
            }
            br.close()
            file.close()
            return all.toString()
            //tvID.text=all
        }
        catch (e: IOException) {
        }
        return ""
    }

    private fun initCart(){
        val userID = readID(filename)
        database.child("users").child(userID).child("Koszyk").addListenerForSingleValueEvent(object :
            ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    koszykTest = dataSnapshot.getValue(KoszykTest::class.java)
                    if(koszykTest?.Dysk_SSD!=null){
                        ivSSD.visibility = View.VISIBLE
                    }
                    if(koszykTest?.Dysk_HDD!=null){
                        ivHDD.visibility = View.VISIBLE
                    }
                    if(koszykTest?.Karta_graficzna!=null){
                        ivKartaGraficzna.visibility = View.VISIBLE
                    }
                    if(koszykTest?.Plyta_glowna!=null){
                        ivMotherboard.visibility = View.VISIBLE
                    }
                    if(koszykTest?.Obudowa!=null){
                        ivObudowa.visibility = View.VISIBLE
                    }
                    if(koszykTest?.Procesor!=null){
                        ivProcesor.visibility = View.VISIBLE
                    }
                    if(koszykTest?.RAM!=null){
                        ivRAM.visibility = View.VISIBLE
                    }
                    if(koszykTest?.Zasilacz!=null){
                        ivZasilacz.visibility = View.VISIBLE
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Snackbar.make(scrollview, databaseError.toException().toString(), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .show()
            }
        })
    }

    private fun hideIcons(){
        ivHDD.visibility = View.INVISIBLE
        ivSSD.visibility = View.INVISIBLE
        ivKartaGraficzna.visibility = View.INVISIBLE
        ivMotherboard.visibility = View.INVISIBLE
        ivObudowa.visibility = View.INVISIBLE
        ivProcesor.visibility = View.INVISIBLE
        ivRAM.visibility = View.INVISIBLE
        ivZasilacz.visibility = View.INVISIBLE
    }
}
