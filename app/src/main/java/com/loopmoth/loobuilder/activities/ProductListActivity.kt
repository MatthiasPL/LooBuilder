package com.loopmoth.loobuilder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopmoth.loobuilder.R
import com.loopmoth.loobuilder.adapters.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_product_list.*
import android.animation.ObjectAnimator
import android.graphics.Color
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.row_item.view.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.loopmoth.loobuilder.classes.KoszykTest
import com.loopmoth.loobuilder.classes.parts.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class ProductListActivity : AppCompatActivity() {

    private var mNames = arrayListOf<String>()
    private var mDescs = arrayListOf<String>()
    private var mPrices = arrayListOf<Double>()
    private var mIcons = arrayListOf<String>()

    private lateinit var database: DatabaseReference
    private val hddArray: MutableList<Dysk_HDD> = mutableListOf()
    private val ssdArray: MutableList<Dysk_SSD> = mutableListOf()
    private val karta_graficznaArray: MutableList<Karta_graficzna> = mutableListOf()
    private val motherboardArray: MutableList<Motherboard> = mutableListOf()
    private val obudowaArray: MutableList<Obudowa> = mutableListOf()
    private val procesorArray: MutableList<Procesor> = mutableListOf()
    private val ramArray: MutableList<RAM> = mutableListOf()
    private val zasilaczArray: MutableList<Zasilacz> = mutableListOf()

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerViewAdapter
    private lateinit var KOMPONENT: String

    private var koszykTest: KoszykTest? = null

    val filename = "userID.conf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        FirebaseApp.initializeApp(this)
        database = FirebaseDatabase.getInstance().reference
    }

    override fun onResume() {
        super.onResume()

        //pobranie informacji o komponencie, ktory chcemy wyswietlic
        val getextra=intent.extras
        KOMPONENT = getextra.getString("ComponentName")
        //Toast.makeText(this,KOMPONENT,Toast.LENGTH_SHORT).show()
        initProducts()
        initCart()
    }

    fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        mRecyclerView = findViewById(R.id.rvProductList)
        mRecyclerView.setLayoutManager(layoutManager)

        //Tutaj tworzymy adapter i podłączamy pod listę
        mAdapter = RecyclerViewAdapter(this, mNames, mDescs, mPrices, mIcons)
        mRecyclerView.setAdapter(mAdapter)
    }


    //funkcja usuwająca zaznaczenia produktów
    fun uncheckIDs(position: Int){
        //index do przechodzenia przez holder trzymający komponenty
        var i = 0

        //ilość elementów w tym kontenerze
        val size = mRecyclerView.getChildCount()

        while (i < size) {
            val holder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i))
            if (holder != null) {

                //odznaczenie wszyskich komponentów poza wybranym
                if(i!=position){
                    holder.itemView.bCheck.setText("WYBIERZ")
                    holder.itemView.bCheck.setBackgroundColor(Color.BLACK)
                }
                else{
                    //wybrany komponent ma inny kolor
                    holder.itemView.bCheck.setBackgroundColor(Color.GRAY)
                }
            }
            i++
        }
    }

    fun perfornClickInHolder(position: Int){
        //index do przechodzenia przez holder trzymający komponenty
        var i = 0

        //ilość elementów w tym kontenerze
        val size = mRecyclerView.getChildCount()

        while (i < size) {
            val holder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i))
            if (holder != null) {

                //kliknięcie wybranego przycisku
                if(i==position){
                    holder.itemView.bCheck.performClick()
                    break
                }
            }
            i++
        }
    }

    private fun initCart(){
        val userID = readID(filename)
        database.child("users").child(userID).child("Koszyk").addListenerForSingleValueEvent(object :ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    koszykTest = dataSnapshot.getValue(KoszykTest::class.java)
                    if(koszykTest?.Dysk_HDD!=null && KOMPONENT == "Dysk_HDD"){
                        perfornClickInHolder(koszykTest?.Dysk_HDD!!)
                    }
                    if(koszykTest?.Dysk_SSD!=null && KOMPONENT == "Dysk_SSD"){
                        perfornClickInHolder(koszykTest?.Dysk_SSD!!)
                    }
                    if(koszykTest?.Karta_graficzna!=null && KOMPONENT == "Karta_graficzna"){
                        perfornClickInHolder(koszykTest?.Karta_graficzna!!)
                    }
                    if(koszykTest?.Plyta_glowna!=null && KOMPONENT == "Plyta_glowna"){
                        perfornClickInHolder(koszykTest?.Plyta_glowna!!)
                    }
                    if(koszykTest?.Obudowa!=null && KOMPONENT == "Obudowa"){
                        perfornClickInHolder(koszykTest?.Obudowa!!)
                    }
                    if(koszykTest?.Procesor!=null && KOMPONENT == "Procesor"){
                        perfornClickInHolder(koszykTest?.Procesor!!)
                    }
                    if(koszykTest?.RAM!=null && KOMPONENT == "RAM"){
                        perfornClickInHolder(koszykTest?.RAM!!)
                    }
                    if(koszykTest?.Zasilacz!=null && KOMPONENT == "Zasilacz"){
                        perfornClickInHolder(koszykTest?.Zasilacz!!)
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

    fun writeToCart(id: Int){
        val userID = readID(filename)
        database.child("users").child(userID).child("Koszyk").child(KOMPONENT).setValue(id)
    }

    private fun initProducts(){
        clearAllProductsArrays()
        database.child("Podzespoly").child(this.KOMPONENT).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                clearAllProductsArrays()

                when{
                    KOMPONENT == "Dysk_HDD" -> {
                        dataSnapshot.children.mapNotNullTo(hddArray) {
                            it.getValue<Dysk_HDD>(Dysk_HDD::class.java)
                        }
                        hddArray.forEach {
                            mNames.add(it.nazwa)
                            mDescs.add("Producent: "+it.producent+"\nInterfejs: "+it.interfejs+"\nPojemność: "+it.pojemnosc_GB+" GB\nFormat dysku: "+it.format_dysku_cale+" \"\nDługość: "+it.dlugosc_mm+" mm\nGrubość: "+it.grubosc_mm+" mm\nSzerokość: "+it.szerokosc_mm+" mm\nWaga: "+it.waga_g+"g")
                            mPrices.add(it.cena)
                            mIcons.add(it.img)
                        }
                    }

                    KOMPONENT == "Dysk_SSD" ->{
                        dataSnapshot.children.mapNotNullTo(ssdArray) {
                            it.getValue<Dysk_SSD>(Dysk_SSD::class.java)
                        }
                        ssdArray.forEach {
                            mNames.add(it.nazwa)
                            mDescs.add("Producent: "+it.producent+"\nInterfejs: "+it.interfejs+"\nPojemność: "+it.pojemnosc_GB+" GB\nFormat dysku: "+it.Format_dysku_cale+"\"\nGrubość: "+it.grubosc_mm+" mm\nSzybkość odczytu: "+it.szybkosc_odczytu_MB_s+" MB/s\nSzybkość zapisu: "+it.szybkosc_zapisu_MB_s+" MB/s")
                            mPrices.add(it.cena)
                            mIcons.add(it.img)
                        }
                    }

                    KOMPONENT == "Karta_graficzna" ->{
                        dataSnapshot.children.mapNotNullTo(karta_graficznaArray) {
                            it.getValue<Karta_graficzna>(Karta_graficzna::class.java)
                        }
                        karta_graficznaArray.forEach {
                            mNames.add(it.nazwa)
                            mDescs.add("Producent: "+it.producent+"\nProducent chipsetu: "+it.producent_chipsetu+"\nRodzaj chipsetu: "+it.rodzaj_chipsetu+"\nDługość: "+it.dlugosc_mm+" mm\nRodzaj pamięci RAM: "+it.rodzaj_pamieci_RAM+"\nIlość pamięci RAM: "+it.ilosc_pamieci_RAM_GB+" GB\nTyp chłodzenia: "+it.typ_chlodzenia+"Typ złącza: "+it.typ_zlacza+"Złącza: "+it.zlacza)
                            mPrices.add(it.cena)
                            mIcons.add(it.img)
                        }
                    }

                    KOMPONENT == "Plyta_glowna" ->{
                        dataSnapshot.children.mapNotNullTo(motherboardArray) {
                            it.getValue<Motherboard>(Motherboard::class.java)
                        }
                        motherboardArray.forEach {
                            mNames.add(it.nazwa)
                            mDescs.add("Producent: "+it.producent+"\nChipset: "+it.chipset+"\nStandard: "+it.standard+"\nStandard pamięci: "+it.standard_pamieci+"\nSloty pamięci: "+it.sloty_pamieci+"\nGniazdo procesora: "+it.gniazdo_procesora+"\nIlość procesorów: "+it.ilosc_procesorow+"\nChipset graficzny: "+it.chipset_graficzny+"\nGłębokość: "+it.glebokosc+" mm\nSzerokość: "+it.szerokosc+" mm\nGniazda rozszerzeń: "+it.gniazda_rozszerzen)
                            mPrices.add(it.cena)
                            mIcons.add(it.img)
                        }
                    }

                    KOMPONENT == "Obudowa" ->{
                        dataSnapshot.children.mapNotNullTo(obudowaArray) {
                            it.getValue<Obudowa>(Obudowa::class.java)
                        }
                        obudowaArray.forEach {
                            mNames.add(it.nazwa)
                            mDescs.add("Producent: "+it.producent+"\nTyp obudowy: "+it.typ_obudowy+"\nKolor: "+it.kolor+"\nSzerokość: "+it.szerokosc_cm+" cm\nWysokoć: "+it.wysokosc_cm+" cm\nGłębokość: "+it.glebokosc_cm+" cm\nWaga: "+it.waga_kg+" kg\nKompatybilność: "+it.kompatybilnosc)
                            mPrices.add(it.cena)
                            mIcons.add(it.img)
                        }
                    }

                    KOMPONENT == "Procesor" ->{
                        dataSnapshot.children.mapNotNullTo(procesorArray) {
                            it.getValue<Procesor>(Procesor::class.java)
                        }
                        procesorArray.forEach {
                            mNames.add(it.nazwa)
                            mDescs.add("Producent: "+it.producent+"\nLinia: "+it.linia+"\nTyp gniazda: "+it.typ_gniazda+"\nTaktowanie: "+it.taktowanie+" GHz\nLiczba rdzeni: "+it.liczba_rdzeni+"\nIlość wątków: "+it.ilosc_watkow+"\nUkład graficzny: "+it.uklad_graficzny)
                            mPrices.add(it.cena)
                            mIcons.add(it.img)
                        }
                    }

                    KOMPONENT == "RAM" ->{
                        dataSnapshot.children.mapNotNullTo(ramArray) {
                            it.getValue<RAM>(RAM::class.java)
                        }
                        ramArray.forEach {
                            mNames.add(it.nazwa)
                            mDescs.add("Producent: "+it.producent+"\nTyp pamięci: "+it.typ_pamieci+"\nPojemność: "+it.pojemnosc_GB+" GB\nLiczba modułów: "+it.liczba_modulow+"\nCzęstotliwość pracy: "+it.czestotliwosc_pracy_MHz+" MHz")
                            mPrices.add(it.cena)
                            mIcons.add(it.img)
                        }
                    }

                    KOMPONENT == "Zasilacz" ->{
                        dataSnapshot.children.mapNotNullTo(zasilaczArray) {
                            it.getValue<Zasilacz>(Zasilacz::class.java)
                        }
                        zasilaczArray.forEach {
                            mNames.add(it.nazwa)
                            mDescs.add("Producent: "+it.producent+"\nStandard: "+it.standard+"\nMoc: "+it.moc_W+" W\nSzerokość: "+it.szerokosc_mm+" mm\nWysokość: "+it.wysokosc_mm+" mm\nGłębokość: "+it.glebokosc_mm+" mm\nZłącza: "+it.zlacza)
                            mPrices.add(it.cena)
                            mIcons.add(it.img)
                        }
                    }
                }
                initRecyclerView()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //Log.e(FragmentActivity.TAG, "onCancelled", databaseError.toException())
                Snackbar.make(scrollview, databaseError.toException().toString(), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .show()
            }
        })
    }

    private fun clearAllProductsArrays(){
        hddArray.clear()
        ssdArray.clear()
        karta_graficznaArray.clear()
        motherboardArray.clear()
        obudowaArray.clear()
        procesorArray.clear()
        ramArray.clear()
        zasilaczArray.clear()

        mNames.clear()
        mDescs.clear()
        mIcons.clear()
        mPrices.clear()
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
