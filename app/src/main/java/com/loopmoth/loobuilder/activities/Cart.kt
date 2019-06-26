package com.loopmoth.loobuilder.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.loopmoth.loobuilder.R
import com.loopmoth.loobuilder.adapters.CartRecyclerViewAdapter
import com.loopmoth.loobuilder.classes.KoszykTest
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_product_list.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class Cart : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: CartRecyclerViewAdapter

    private var mNames = arrayListOf<String>()
    private var mPrices = arrayListOf<Double>()
    private var mIcons = arrayListOf<String>()

    private lateinit var database: DatabaseReference

    private var koszykTest: KoszykTest? = null

    val filename = "userID.conf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        FirebaseApp.initializeApp(this)
        database = FirebaseDatabase.getInstance().reference

        initCart()
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        mRecyclerView = findViewById(R.id.rvCartProductList)
        mRecyclerView.setLayoutManager(layoutManager)

        //Tutaj tworzymy adapter i podłączamy pod listę
        mAdapter = CartRecyclerViewAdapter(this, mNames, mPrices, mIcons)
        mRecyclerView.setAdapter(mAdapter)
    }

    private fun initCart(){
        clearAllProductsArrays()
        val userID = readID(filename)
        database.child("users").child(userID).child("Koszyk").addListenerForSingleValueEvent(object :
            ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                clearAllProductsArrays()
                if (dataSnapshot.exists()) {
                    koszykTest = dataSnapshot.getValue(KoszykTest::class.java)
                    if(koszykTest?.Dysk_SSD!=null){
                        add_component("Dysk_SSD", koszykTest?.Dysk_SSD!!)
                    }
                    if(koszykTest?.Dysk_HDD!=null){
                        add_component("Dysk_HDD", koszykTest?.Dysk_HDD!!)
                    }
                    if(koszykTest?.Karta_graficzna!=null){
                        add_component("Karta_graficzna", koszykTest?.Karta_graficzna!!)
                    }
                    if(koszykTest?.Plyta_glowna!=null){
                        add_component("Plyta_glowna", koszykTest?.Plyta_glowna!!)
                    }
                    if(koszykTest?.Obudowa!=null){
                        add_component("Obudowa", koszykTest?.Obudowa!!)
                    }
                    if(koszykTest?.Procesor!=null){
                        add_component("Procesor", koszykTest?.Procesor!!)
                    }
                    if(koszykTest?.RAM!=null){
                        add_component("RAM", koszykTest?.RAM!!)
                    }
                    if(koszykTest?.Zasilacz!=null){
                        add_component("Zasilacz", koszykTest?.Zasilacz!!)
                    }
                    initRecyclerView()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Snackbar.make(scrollview, databaseError.toException().toString(), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .show()
            }
        })
        initRecyclerView()
    }

    private fun add_component(type: String, id:Int){
        database.child("Podzespoly").child(type + "/" + id.toString()).addListenerForSingleValueEvent(object :
            ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Toast.makeText(this@Cart, "Działa w add_component" + dataSnapshot.child("nazwa").getValue(String::class.java)!!, Toast.LENGTH_SHORT).show()
                    mNames.add(dataSnapshot.child("nazwa").getValue(String::class.java)!!)
                    mIcons.add(dataSnapshot.child("img").getValue(String::class.java)!!)
                    mPrices.add(dataSnapshot.child("cena").getValue(Double::class.java)!!)
                }
                else{
                    Toast.makeText(this@Cart, "Zmaściłeś baranie", Toast.LENGTH_SHORT).show()
                }
                initRecyclerView()
                sumAll()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Snackbar.make(scrollview, databaseError.toException().toString(), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .show()
            }
        })
    }

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

    private fun clearAllProductsArrays(){
        mNames.clear()
        mIcons.clear()
        mPrices.clear()
    }

    @SuppressLint("SetTextI18n")
    private fun sumAll(){
        var suma: Double = 0.0

        mPrices.forEach {
            suma += it
        }

        tvSuma.text = "%.2f".format(suma) + " zł"
    }
}
