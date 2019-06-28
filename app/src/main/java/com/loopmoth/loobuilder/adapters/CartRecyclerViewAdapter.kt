package com.loopmoth.loobuilder.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import android.content.ContextWrapper
import android.widget.LinearLayout
import com.loopmoth.loobuilder.activities.ProductListActivity
import com.loopmoth.loobuilder.R

//adapter do załadowania listy produktów z bazy
class CartRecyclerViewAdapter(private val mContext: Context, names: ArrayList<String>, prices: ArrayList<Double>, icons: ArrayList<String>) :
    RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>() {

    //vars
    private var mNames = arrayListOf<String>()
    private var mPrice = arrayListOf<Double>()
    private var mIcons = arrayListOf<String>()
    private var mColors = arrayListOf<Int>()

    private var mFirstViewHolderID = 0
    private var mLastViewHolderID = 0

    private var mChecks = arrayListOf<Boolean>()

    init {
        mNames = names
        mPrice = prices
        mIcons = icons

        mNames.forEach{
            mChecks.add(false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_cart, parent, false)
        return CartViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        //załadowanie tekstu
        holder.mName.text = mNames.get(position)
        holder.mPrice.text = "%.2f".format(mPrice.get(position)) + " zł"

        mLastViewHolderID = position

        //załadowanie obrazków
        Picasso
            .get()
            .load(mIcons.get(position))
            .resize(64,64)
            .into(holder.mIcons)
    }

    override fun getItemCount(): Int {
        return mNames.size
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var mName: TextView
        internal var mPrice: TextView
        internal var mIcons: ImageView

        init {
            mName = itemView.findViewById(R.id.tvProductName)
            mPrice = itemView.findViewById(R.id.tvProductPrice)
            mIcons = itemView.findViewById(R.id.ivProductPhoto)
        }
    }

    companion object {
        private val TAG = "CartRecyclerViewAdapter"
    }
}