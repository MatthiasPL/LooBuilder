package com.loopmoth.loobuilder.activities

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
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
import java.util.*

class Cart : AppCompatActivity(), TextToSpeech.OnInitListener, SensorEventListener {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: CartRecyclerViewAdapter

    private var mNames = arrayListOf<String>()
    private var mPrices = arrayListOf<Double>()
    private var mIcons = arrayListOf<String>()

    private lateinit var database: DatabaseReference

    private var koszykTest: KoszykTest? = null

    val filename = "userID.conf"

    private var tts: TextToSpeech? = null

    private var text: String = ""

    lateinit var sm: SensorManager

    var acelVal:Float=0F
    var acelLast:Float=0F
    var shake:Float=0F
    var LastSize=20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        FirebaseApp.initializeApp(this)
        database = FirebaseDatabase.getInstance().reference

        tts = TextToSpeech(this, this)

        bSpeak.setOnClickListener { speakOut(text) }

        initCart()

        sm=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sm.registerListener(this@Cart, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal=SensorManager.GRAVITY_EARTH
        acelLast=SensorManager.GRAVITY_EARTH
        shake=0.00f
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val x = event!!.values[0]
        val y = event.values[1]
        val z = event.values[2]

        acelLast=acelVal
        acelVal= Math.sqrt((x*x+y*y+z*z).toDouble()).toFloat()
        val delta=acelVal-acelLast
        shake=shake*0.9F+delta

        if(shake>12)
        {
            bClear.performClick()
        }
    }

    override fun onPause() {
        super.onPause()
        sm.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()

        bClear.setOnClickListener {
            clearAllProductsArrays()

            val userID = readID(filename)
            database.child("users").child(userID).child("Koszyk").setValue(null)

            initRecyclerView()
            sumAll()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.GERMAN)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
                bSpeak.isEnabled = false
            } else {
                bSpeak.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
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

                    text += type + " " + dataSnapshot.child("nazwa").getValue(String::class.java)!!
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
        text = ""
    }

    @SuppressLint("SetTextI18n")
    private fun sumAll(){
        var suma: Double = 0.0

        mPrices.forEach {
            suma += it
        }

        tvSuma.text = "%.2f".format(suma) + " zł"
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_ADD, null,"")
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}
