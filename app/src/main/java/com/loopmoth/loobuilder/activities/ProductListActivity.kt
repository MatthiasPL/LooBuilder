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

        mRecyclerView = findViewById(R.id.rvProductList)
        mRecyclerView.setLayoutManager(layoutManager)

        mAdapter = RecyclerViewAdapter(this, mNames, mDescs, mPrices, mIcons)
        mRecyclerView.setAdapter(mAdapter)
    }

    fun uncheckIDs(position: Int){
        var i = 0
        val size = mRecyclerView.getChildCount()
        while (i < size) {
            val holder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i))
            if (holder != null) {
                if(i!=position){
                    holder.itemView.bCheck.setText("WYBIERZ")
                    holder.itemView.bCheck.setBackgroundColor(Color.BLACK)
                }
                else{
                    holder.itemView.bCheck.setBackgroundColor(Color.GRAY)
                }
            }
            i++
        }
    }
}
