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
import com.loopmoth.loobuilder.activities.ProductListActivity
import com.loopmoth.loobuilder.R

class RecyclerViewAdapter(private val mContext: Context, names: ArrayList<String>, descs: ArrayList<String>, prices: ArrayList<Double>, icons: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    //vars
    private var mNames = arrayListOf<String>()
    private var mDescs = arrayListOf<String>()
    private var mPrice = arrayListOf<Double>()
    private var mIcons = arrayListOf<String>()

    private var mCounts = arrayListOf<Int>()

    private var sum: Double = 0.0

    init {
        mNames = names
        mDescs = descs
        mPrice = prices
        mIcons = icons

        mNames.forEach{
            mCounts.add(0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mName.text = mNames.get(position)
        holder.mDesc.text = mDescs.get(position)
        holder.mPrice.text = "%.2f".format(mPrice.get(position)) + " zł"
        holder.mCount.text = mCounts.get(position).toString()

        Picasso
            .get()
            .load(mIcons.get(position))
            .resize(128,128)
            .into(holder.mIcons)

        holder.bAdd.setOnClickListener {
            val count = holder.mCount.text.toString().toInt() + 1
            holder.mCount.text = count.toString()
            mCounts[position] = count

            val ma = getMainActivity(mContext)
            if(ma!=null){
                ma.changeSum(getSum())
            }
            else{
                Toast.makeText(mContext, "Błąd. Nie odnaleziono widoku nadrzędnego (ProductListActivity).", Toast.LENGTH_SHORT).show()
            }
        }

        holder.bSub.setOnClickListener {
            val count = holder.mCount.text.toString().toInt() - 1
            holder.mCount.text = count.toString()
            mCounts[position] = count

            val ma = getMainActivity(mContext)
            if(ma!=null){
                ma.changeSum(getSum())
            }
            else{
                Toast.makeText(mContext, "Błąd. Nie odnaleziono widoku nadrzędnego (ProductListActivity).", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return mNames.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var mName: TextView
        internal var mDesc: TextView
        internal var mPrice: TextView
        internal var mIcons: ImageView

        internal var mCount: TextView
        internal var bAdd: Button
        internal var bSub: Button

        init {
            mName = itemView.findViewById(R.id.tvProductName)
            mDesc = itemView.findViewById(R.id.tvProductDesc)
            mPrice = itemView.findViewById(R.id.tvProductPrice)
            mIcons = itemView.findViewById(R.id.ivProductPhoto)

            mCount = itemView.findViewById(R.id.tvCount)
            bAdd = itemView.findViewById(R.id.bAdd)
            bSub = itemView.findViewById(R.id.bSub)
        }
    }

    companion object {
        private val TAG = "RecyclerViewAdapter"
    }

    fun getSum(): Double{
        var suma = 0.0
        for (i in mNames.indices){
            suma += mPrice[i] * mCounts[i]
        }
        return suma
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
}