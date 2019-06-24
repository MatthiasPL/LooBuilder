package com.loopmoth.loobuilder.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.util.TypedValue
import android.view.View
import com.loopmoth.loobuilder.R
import com.loopmoth.loobuilder.adapters.UltraPagerAdapter
import com.tmall.ultraviewpager.UltraViewPager
import kotlinx.android.synthetic.main.activity_help.*

class HelpActivity : AppCompatActivity() {
    //TODO: zrobienie pomocy dla użytkownika

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }

    override fun onResume() {
        super.onResume()

        val ultraViewPager = findViewById<View>(R.id.ultra_viewpager) as UltraViewPager
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL)
        //initialize UltraPagerAdapter，and add child view to UltraViewPager
        val adapter = UltraPagerAdapter(true)
        ultraViewPager.adapter = adapter

        //initialize built-in indicator
        ultraViewPager.initIndicator()
        //set style of indicators
        ultraViewPager.indicator
            .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
            .setFocusColor(Color.BLACK)
            .setNormalColor(Color.LTGRAY)
            .setRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, resources.displayMetrics).toInt())
        //set the alignment
        ultraViewPager.indicator.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        ultraViewPager.indicator.setMargin(0,0,0,30)
        //construct built-in indicator, and add it to  UltraViewPager
        ultraViewPager.indicator.build()

        //set an infinite loop
        //ultraViewPager.setInfiniteLoop(true)
        //enable auto-scroll mode
        //ultraViewPager.setAutoScroll(2000)

        fab.setOnClickListener{
            finish()
        }
    }
}
