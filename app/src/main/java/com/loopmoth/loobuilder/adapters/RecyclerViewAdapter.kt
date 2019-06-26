package com.loopmoth.loobuilder.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import android.content.ContextWrapper
import android.graphics.Color
import com.google.android.material.snackbar.Snackbar
import com.loopmoth.loobuilder.activities.ProductListActivity
import com.loopmoth.loobuilder.R
import com.loopmoth.loobuilder.R.id.tvProductName
import com.loopmoth.loobuilder.activities.MainActivity
import com.loopmoth.loobuilder.classes.SerializeCart
import com.loopmoth.loobuilder.interfaces.ComputerPart

//adapter do załadowania listy produktów z bazy
class RecyclerViewAdapter(private val mContext: Context, names: ArrayList<String>, descs: ArrayList<String>, prices: ArrayList<Double>, icons: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    //vars
    private var mNames = arrayListOf<String>()
    private var mDescs = arrayListOf<String>()
    private var mPrice = arrayListOf<Double>()
    private var mIcons = arrayListOf<String>()

    private var mFirstViewHolderID = 0
    private var mLastViewHolderID = 0

    private var mChecks = arrayListOf<Boolean>()

    init {
        mNames = names
        mDescs = descs
        mPrice = prices
        mIcons = icons

        mNames.forEach{
            mChecks.add(false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //załadowanie tekstu
        holder.mName.text = mNames.get(position)
        holder.mDesc.text = mDescs.get(position)
        holder.mPrice.text = "%.2f".format(mPrice.get(position)) + " zł"

        mLastViewHolderID = position

        //załadowanie obrazków
        Picasso
            .get()
            .load(mIcons.get(position))
            .resize(128,128)
            .into(holder.mIcons)

        //przy zaznaczeniu przycisku wywoływana jest funckja uncheckIDs z aktywności, która odznacza wszystkie elementy
        // poza wybranym

        // ilość produktu
        holder.bCheck.setOnClickListener {view ->
            mChecks[position] = ! mChecks[position]

            //odznaczenie ID w lokalnej tablicy
            uncheckRestIDs(position)

            //referencja do aktywności
            val ma = getMainActivity(mContext)
            if(ma!=null){
                ma.uncheckIDs(position)
                ma.writeToCart(position)
            }
            else{
                Toast.makeText(mContext, "Błąd. Nie odnaleziono widoku nadrzędnego (ProductListActivity).", Toast.LENGTH_SHORT).show()
            }

            //zmiana tekstu przycisków
            //TODO: zmiana w bazie ID
            if(mChecks[position]){
                //wybrany przycisk ma poniższy tekst
                holder.bCheck.setText("ODZNACZ")

                //dodawanie do koszyka produktu

                Snackbar.make(view, mNames[position]+ " dodano do koszyka", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .show()
            }
            else{
                holder.bCheck.setText("WYBIERZ")
                holder.bCheck.setBackgroundColor(Color.BLACK)
                val ma = getMainActivity(mContext)
                if(ma!=null){
                    ma.removeFromCart(position)
                }
                else{
                    Toast.makeText(mContext, "Błąd. Nie odnaleziono widoku nadrzędnego (ProductListActivity).", Toast.LENGTH_SHORT).show()
                }

                Snackbar.make(view, mNames[position]+ " usunięto z koszyka", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .show()
            }
        }

        /*holder.bCheck.setOnClickListener {
            val ma = getMainActivity(mContext)
            if(ma!=null){
                ma.changeSum(getSum())
            }
            else{
                Toast.makeText(mContext, "Błąd. Nie odnaleziono widoku nadrzędnego (ProductListActivity).", Toast.LENGTH_SHORT).show()
            }
        }*/
    }

    override fun getItemCount(): Int {
        return mNames.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var mName: TextView
        internal var mDesc: TextView
        internal var mPrice: TextView
        internal var mIcons: ImageView

        internal var bCheck: Button

        init {
            mName = itemView.findViewById(R.id.tvProductName)
            mDesc = itemView.findViewById(R.id.tvProductDesc)
            mPrice = itemView.findViewById(R.id.tvProductPrice)
            mIcons = itemView.findViewById(R.id.ivProductPhoto)
            bCheck = itemView.findViewById(R.id.bCheck)
        }
    }

    companion object {
        private val TAG = "RecyclerViewAdapter"
    }

    private fun getMainActivity(context: Context): ProductListActivity?{
        if (context == null) {
            return null
        } else if (context is ContextWrapper) {
            return if (context is ProductListActivity) {
                context as ProductListActivity
            } else {
                getMainActivity((context as ContextWrapper).baseContext)
            }
        }
        return null
    }

    private fun uncheckRestIDs(id: Int){
        for (i in mChecks.indices){
            if(i!=id){
                mChecks[i] = false
            }
        }
    }
}