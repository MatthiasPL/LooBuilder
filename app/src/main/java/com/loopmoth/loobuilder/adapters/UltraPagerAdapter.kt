package com.loopmoth.loobuilder.adapters

import android.graphics.Color
import android.widget.LinearLayout
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import com.loopmoth.loobuilder.R


class UltraPagerAdapter(private val isMultiScr: Boolean) : PagerAdapter() {

    override fun getCount(): Int {
        return 5
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val linearLayout = LayoutInflater.from(container.context).inflate(R.layout.layout_child, null) as LinearLayout
        //new LinearLayout(container.getContext());
        val textView = linearLayout.findViewById(R.id.pager_textview) as TextView
        textView.text = position.toString() + ""
        linearLayout.id = R.id.item_id
        when (position) {
            0 -> linearLayout.setBackgroundColor(Color.parseColor("#2196F3"))
            1 -> linearLayout.setBackgroundColor(Color.parseColor("#673AB7"))
            2 -> linearLayout.setBackgroundColor(Color.parseColor("#009688"))
            3 -> linearLayout.setBackgroundColor(Color.parseColor("#607D8B"))
            4 -> linearLayout.setBackgroundColor(Color.parseColor("#F44336"))
        }
        container.addView(linearLayout)
        //        linearLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, container.getContext().getResources().getDisplayMetrics());
        //        linearLayout.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, container.getContext().getResources().getDisplayMetrics());
        return linearLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as LinearLayout
        container.removeView(view)
    }
}