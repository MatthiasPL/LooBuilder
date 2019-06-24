package com.loopmoth.loobuilder.adapters

import android.graphics.Color
import android.widget.LinearLayout
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import com.loopmoth.loobuilder.R

//adapter ekranu pomocy
//TODO framenty z pomocą
class UltraPagerAdapter(private val isMultiScr: Boolean) : PagerAdapter() {

    //można tutaj zmienić na wielkość arraya z fragmentami zadeklarowanego gdzieś statycznie
    override fun getCount(): Int {
        return 5
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    //załadowanie widoku
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val linearLayout = LayoutInflater.from(container.context).inflate(R.layout.layout_child, null) as LinearLayout
        //new LinearLayout(container.getContext());
        val textView = linearLayout.findViewById(R.id.pager_textview) as TextView

        linearLayout.id = R.id.item_id
        when (position) {
            //można zmienić kolory tła podczas wczytywania, tak jak i tekst
            0 -> {linearLayout.setBackgroundColor(Color.parseColor("#2196F3"))
                textView.text = "Dobierz części komputera wybierając je z list dostępnych pod konkretnymi elementami."}
            1 -> {linearLayout.setBackgroundColor(Color.parseColor("#673AB7"))
                textView.text = "Dodaj je do koszyka klikając przycisk WYBIERZ."}
            2 -> {
                linearLayout.setBackgroundColor(Color.parseColor("#009688"))
                textView.text = "Przejdź do koszyka, aby sprawdzić podsumowanie wybranych elementów."}
            3 -> {linearLayout.setBackgroundColor(Color.parseColor("#607D8B"))
                textView.text = "Przejdź do sekcji sprawdzania wybranych części. Tu dostępny jest schemat budowy komputera."}
            4 -> {linearLayout.setBackgroundColor(Color.parseColor("#F44336"))
                textView.text = "Kolory tła danych części sugerują ich poprawne lub niepoprawne dopasowanie. Po kliknięciu danego elementu dostępna jest informacja o błędzie."}
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