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
import kotlinx.android.synthetic.main.row_item.view.*


class ProductListActivity : AppCompatActivity() {

    private var mNames = arrayListOf<String>()
    private var mDescs = arrayListOf<String>()
    private var mPrices = arrayListOf<Double>()
    private var mIcons = arrayListOf<String>()

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        mNames.add("Testowy produkt 1")
        mDescs.add("Przykładowy opis 1")
        mPrices.add(75.0)
        mIcons.add("https://proline.pl/pic/bg021_0.jpg")

        mNames.add("Testowy produkt 2")
        mDescs.add("Przykładowy opis 2")
        mPrices.add(75.0)
        mIcons.add("https://proline.pl/pic/bg021_0.jpg")

        mNames.add("Testowy produkt 3")
        mDescs.add("Przykładowy opis 3")
        mPrices.add(75.0)
        mIcons.add("https://proline.pl/pic/bg021_0.jpg")
    }

    override fun onResume() {
        super.onResume()

        //TODO: Dodanie produktów z firebase

        initRecyclerView()
    }

    fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        mRecyclerView = findViewById(R.id.rvProductList)
        mRecyclerView.setLayoutManager(layoutManager)

        //Tutaj tworzymy adapter i podłączamy pod listę
        //TODO zmiana argumentów RecyclerViewAdapter na obiekt który sobie zrzutujemy z bazy
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
}
