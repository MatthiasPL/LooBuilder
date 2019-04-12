package com.loopmoth.loobuilder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopmoth.loobuilder.R
import com.loopmoth.loobuilder.adapters.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductListActivity : AppCompatActivity() {

    private var mNames = arrayListOf<String>()
    private var mDescs = arrayListOf<String>()
    private var mPrices = arrayListOf<Double>()
    private var mIcons = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        mNames.add("Testowy produkt")
        mDescs.add("Przykładowy opis")
        mPrices.add(75.0)
        mIcons.add("https://proline.pl/pic/bg021_0.jpg")

        mNames.add("Testowy produkt")
        mDescs.add("Przykładowy opis")
        mPrices.add(75.0)
        mIcons.add("https://proline.pl/pic/bg021_0.jpg")

        mNames.add("Testowy produkt")
        mDescs.add("Przykładowy opis")
        mPrices.add(75.0)
        mIcons.add("https://proline.pl/pic/bg021_0.jpg")
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val recyclerView: RecyclerView = findViewById(R.id.rvProductList)
        recyclerView.setLayoutManager(layoutManager)

        val adapter = RecyclerViewAdapter(this, mNames, mDescs, mPrices, mIcons)
        recyclerView.setAdapter(adapter)
    }

    fun changeSum(sum: Double){
        tvSum.text = "%.2f".format(sum) + " zł"
    }
}
