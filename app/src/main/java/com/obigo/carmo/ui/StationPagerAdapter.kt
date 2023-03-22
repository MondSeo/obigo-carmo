package com.obigo.carmo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.obigo.R

class StationPagerAdapter(
    private val quotes: List<String>
): RecyclerView.Adapter<StationPagerAdapter.QuoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QuoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quote, parent, false)
        )

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val actual_position = position % quotes.size
        holder.bind(quotes[actual_position])
    }

    override fun getItemCount() = Int.MAX_VALUE

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val quoteTextView: TextView = itemView.findViewById<TextView>(R.id.quoteTextView)

        fun bind(station:String) {
            quoteTextView.text = "\"${station}\""


        }
    }

}