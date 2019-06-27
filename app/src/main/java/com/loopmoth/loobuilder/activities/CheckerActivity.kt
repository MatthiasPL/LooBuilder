package com.loopmoth.loobuilder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.loopmoth.loobuilder.R
import com.loopmoth.loobuilder.classes.KoszykTest
import kotlinx.android.synthetic.main.activity_product_list.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.loopmoth.loobuilder.classes.parts.*
import com.loopmoth.loobuilder.classes.subclasses.GniazdoRozszerzen
import com.loopmoth.loobuilder.classes.subclasses.Kompatybilnosc
import kotlinx.android.synthetic.main.activity_checker.*

class CheckerActivity : AppCompatActivity() {

    private var koszykTest: KoszykTest? = null
    val filename = "userID.conf"
    private lateinit var database: DatabaseReference
    private var Dysk_SSD_nazwa: String?=null
    private var Dysk_HDD_nazwa: String?=null
    private var Karta_graficzna_typ_zlacza: String? = null
    private var mMotherboard: Motherboard?=null
    private var Procesor_typ_gniazda: String?=null
    private var RAM_typ_pamieci: String?=null
    private var mObudowa: Obudowa?=null
    private var Zasilacz_standard: String?=null
    private var GniazdoRozszerzenNazwy: MutableList<String>?=null
    private var KompatybilnoscNazwy: MutableList<String>?=null
    private var CompatibilityList= mutableListOf(CompatibilityOfElements.ABSENT,CompatibilityOfElements.ABSENT,CompatibilityOfElements.ABSENT,CompatibilityOfElements.ABSENT)
    // 0 procesor-plytaglowna , 1 ram-plytaglowna, 2 kartagraf-plytaglowna, 3 obudowa-zasilacz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checker)

        FirebaseApp.initializeApp(this)
        database = FirebaseDatabase.getInstance().reference

    }

    override fun onResume() {
        super.onResume()

        //Toast.makeText(this, CompatibilityList[0].toString()+ " " + CompatibilityList[1].toString() + " " + CompatibilityList[2].toString() + " " + CompatibilityList[3].toString(),Toast.LENGTH_SHORT).show()

        deleteColors()
        initCart()
        CheckCompatibility()
        SetColorsAndErrors(CompatibilityList)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.getItemId()

        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteColors(){
        DyskHDDIV.setBackgroundResource(R.drawable.border)
        DyskSSDIV.setBackgroundResource(R.drawable.border)
        GraficznaIV.setBackgroundResource(R.drawable.border)
        PlytaglownaIV.setBackgroundResource(R.drawable.border)
        ObudowaIV.setBackgroundResource(android.R.color.darker_gray)
        ProcesorIV.setBackgroundResource(R.drawable.border)
        RAMIV.setBackgroundResource(R.drawable.border)
        ZasilaczIV.setBackgroundResource(R.drawable.border)
        ErrorConstraintLayout.visibility= View.GONE
    }

    private fun initCart(){
        val userID = readID(filename)
        database.child("users").child(userID).child("Koszyk").addListenerForSingleValueEvent(object :ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    koszykTest = dataSnapshot.getValue(KoszykTest::class.java)
                    if(koszykTest?.Dysk_HDD!=null) {
                        initData("Dysk_HDD", koszykTest?.Dysk_HDD!!)
                    }
                    if(koszykTest?.Dysk_SSD!=null) {
                        initData("Dysk_SSD", koszykTest?.Dysk_SSD!!)
                    }
                    if(koszykTest?.Plyta_glowna!=null) {
                        initData("Plyta_glowna", koszykTest?.Plyta_glowna!!)
                        if (koszykTest?.Karta_graficzna != null) {
                            initData("Karta_graficzna", koszykTest?.Karta_graficzna!!)
                        }
                        if (koszykTest?.Procesor != null) {
                            initData("Procesor", koszykTest?.Procesor!!)
                        }
                        if (koszykTest?.RAM != null) {
                            initData("RAM", koszykTest?.RAM!!)
                        }
                    }
                    if(koszykTest?.Obudowa!=null) {
                        initData("Obudowa", koszykTest?.Obudowa!!)
                        if(koszykTest?.Zasilacz!=null) {
                            initData("Zasilacz", koszykTest?.Zasilacz!!)
                        }
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

    private fun initData(type: String, id:Int){
        database.child("Podzespoly").child(type + "/" + id.toString()).addListenerForSingleValueEvent(object :
            ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    when(type){
                        "Dysk_HDD" -> {
                            Dysk_HDD_nazwa=dataSnapshot.child("nazwa").getValue(String::class.java)!!
                        }
                        "Dysk_SSD" -> {
                            Dysk_SSD_nazwa=dataSnapshot.child("nazwa").getValue(String::class.java)!!
                        }
                        "Karta_graficzna" ->{
                            Karta_graficzna_typ_zlacza=dataSnapshot.child("typ_zlacza").getValue(String::class.java)!!
                        }
                        "Plyta_glowna" ->{
                            //Motherboard_gniazda_rozszerzen=dataSnapshot.child("gniazda_rozszerzen").getValue(List<GniazdoRozszerzen>)
                            mMotherboard=dataSnapshot.getValue(Motherboard::class.java)
                            //Toast.makeText(this@CheckerActivity, "Działa w add_component" + mMotherboard!!.gniazda_rozszerzen[1].nazwa, Toast.LENGTH_SHORT).show()
                        }
                        "Procesor" -> {
                            Procesor_typ_gniazda=dataSnapshot.child("typ_gniazda").getValue(String::class.java)
                        }
                        "RAM" -> {
                            RAM_typ_pamieci=dataSnapshot.child("typ_pamieci").getValue(String::class.java)
                        }
                        "Obudowa" -> {
                            mObudowa=dataSnapshot.getValue(Obudowa::class.java)
                        }
                        "Zasilacz" -> {
                            Zasilacz_standard=dataSnapshot.child("standard").getValue(String::class.java)
                        }
                    }

                    //Toast.makeText(this@CheckerActivity, "Działa w add_component" + Karta_graficzna_typ_zlacza, Toast.LENGTH_SHORT).show()
                    /*mNames.add(dataSnapshot.child("nazwa").getValue(String::class.java)!!)
                    mIcons.add(dataSnapshot.child("img").getValue(String::class.java)!!)
                    mPrices.add(dataSnapshot.child("cena").getValue(Double::class.java)!!)*/
                }
                else{
                    Toast.makeText(this@CheckerActivity, "Zmaściłeś baranie", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Snackbar.make(scrollview, databaseError.toException().toString(), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .show()
            }
        })
    }

    private fun CheckCompatibility(){

        if(mMotherboard!=null){
            if(Procesor_typ_gniazda!=null) {
                CompatibilityList[0]=IsSameInfo(Procesor_typ_gniazda!!, mMotherboard!!.gniazdo_procesora)
                Toast.makeText(this, Procesor_typ_gniazda, Toast.LENGTH_SHORT).show()
            }
            if(RAM_typ_pamieci!=null){
                Toast.makeText(this, RAM_typ_pamieci, Toast.LENGTH_SHORT).show()
                CompatibilityList[1]=IsSameInfo(RAM_typ_pamieci!!, mMotherboard!!.standard_pamieci)
            }
            if(Karta_graficzna_typ_zlacza!=null){
                CompatibilityList[2]=HasRozszerzenie(mMotherboard!!.gniazda_rozszerzen,Karta_graficzna_typ_zlacza!!)
            }
        }
        if(mObudowa!=null){
            if(Zasilacz_standard!=null){
                CompatibilityList[3]=IsCompatible(mObudowa!!.kompatybilnosc, Zasilacz_standard!!)
            }
        }
    }

    private fun IsSameInfo(info1: String, info2: String): CompatibilityOfElements{
        if(info1==info2)
            return CompatibilityOfElements.COMPATIBLE
        if(info1!=info2)
            return CompatibilityOfElements.NOT_COMPATIBLE
        return CompatibilityOfElements.ABSENT
    }

    private fun HasRozszerzenie(list: List<GniazdoRozszerzen>, typ_zlacza: String): CompatibilityOfElements{
        for(i in list) {
            if(i.nazwa==typ_zlacza) return CompatibilityOfElements.COMPATIBLE
        }
        return CompatibilityOfElements.NOT_COMPATIBLE
    }

    private fun IsCompatible(list: List<Kompatybilnosc>, standard: String): CompatibilityOfElements{
        for(i in list) {
            if(i.nazwa==standard) return CompatibilityOfElements.COMPATIBLE
        }
        return CompatibilityOfElements.NOT_COMPATIBLE
    }

    private fun SetColorsAndErrors(array: List<CompatibilityOfElements>) {
        // 0 procesor-plytaglowna , 1 ram-plytaglowna, 2 kartagraf-plytaglowna, 3 obudowa-zasilacz

        //Toast.makeText(this, "a " + CompatibilityList[0].toString()+ " " + CompatibilityList[1].toString() + " " + CompatibilityList[2].toString() + " " + CompatibilityList[3].toString(),Toast.LENGTH_SHORT).show()

        if(array[0]==CompatibilityOfElements.COMPATIBLE&&array[1]==CompatibilityOfElements.COMPATIBLE&&array[2]==CompatibilityOfElements.COMPATIBLE)
            PlytaglownaIV.setBackgroundResource(android.R.color.holo_green_dark)
        else if(array[0]==CompatibilityOfElements.NOT_COMPATIBLE||array[1]==CompatibilityOfElements.NOT_COMPATIBLE||array[2]==CompatibilityOfElements.NOT_COMPATIBLE)
            PlytaglownaIV.setBackgroundResource(android.R.color.holo_red_dark)
        when(array[0]){
            CompatibilityOfElements.COMPATIBLE -> {
                ProcesorIV.setBackgroundResource(android.R.color.holo_green_dark)
                //PlytaglownaIV.setBackgroundResource(android.R.color.holo_green_dark)
            }
            CompatibilityOfElements.NOT_COMPATIBLE -> {
                ProcesorIV.setBackgroundResource(android.R.color.holo_red_light)
                //PlytaglownaIV.setBackgroundResource(android.R.color.holo_red_dark)
                ProcesorIV.setOnClickListener {
                    SetErrorText("Procesor", "Płyta główna", Procesor_typ_gniazda!!, listOf(mMotherboard!!.gniazdo_procesora))
                }
            }
            else -> {

            }
        }
        when (array[1]) {
            CompatibilityOfElements.COMPATIBLE -> {
                RAMIV.setBackgroundResource(android.R.color.holo_green_dark)
                //PlytaglownaIV.setBackgroundResource(android.R.color.holo_green_dark)
            }
            CompatibilityOfElements.NOT_COMPATIBLE -> {
                RAMIV.setBackgroundResource(android.R.color.holo_red_light)
                //PlytaglownaIV.setBackgroundResource(android.R.color.holo_red_dark)
                RAMIV.setOnClickListener {
                    SetErrorText(
                        "Pamięć RAM",
                        "Płyta główna",
                        RAM_typ_pamieci!!,
                        listOf(mMotherboard!!.standard_pamieci)
                    )
                }
            }
            else -> {

            }
        }

        when(array[2]){
            CompatibilityOfElements.COMPATIBLE -> {
                GraficznaIV.setBackgroundResource(android.R.color.holo_green_dark)
            }
            CompatibilityOfElements.NOT_COMPATIBLE -> {
                GraficznaIV.setBackgroundResource(android.R.color.holo_red_light)
                GraficznaIV.setOnClickListener {
                    for(x in mMotherboard!!.gniazda_rozszerzen)
                        GniazdoRozszerzenNazwy!!.add(x.nazwa)
                    SetErrorText("Karta graficzna", "Płyta główna", Karta_graficzna_typ_zlacza!!, GniazdoRozszerzenNazwy!!.toList())
                }
            }
            else -> {

            }
        }
        when(array[3]){
            CompatibilityOfElements.COMPATIBLE -> {
                ObudowaIV.setBackgroundResource(android.R.color.holo_green_dark)
                ZasilaczIV.setBackgroundResource(android.R.color.holo_green_dark)
            }
            CompatibilityOfElements.NOT_COMPATIBLE -> {
                ObudowaIV.setBackgroundResource(android.R.color.holo_red_dark)
                ZasilaczIV.setBackgroundResource(android.R.color.holo_red_light)
                ZasilaczIV.setOnClickListener {
                    for(x in  mObudowa!!.kompatybilnosc)
                        KompatybilnoscNazwy!!.add(x.nazwa)
                    SetErrorText("Zasilacz", "Obudowa", Zasilacz_standard!!, KompatybilnoscNazwy!!)
                }
            }
            else -> {

            }
        }
        if(Dysk_HDD_nazwa!=null)
            DyskHDDIV.setBackgroundResource(android.R.color.holo_green_dark)
        if(Dysk_SSD_nazwa!=null)
            DyskSSDIV.setBackgroundResource(android.R.color.holo_green_dark)
    }

    private fun SetErrorText(part1: String, part2: String, component1: String, componentList: List<String>){
        ErrorConstraintLayout.visibility= View.VISIBLE
        PartName1TV.text=part1
        Component1TV.text=component1
        PartName2TV.text=part2
        Component2TV.text=""
        for(a in componentList)
            Component2TV.append(a + "\n")
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
}
