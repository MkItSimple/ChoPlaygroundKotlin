package com.example.choplaygroundkotlin.portfolio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.choplaygroundkotlin.R
import kotlinx.android.synthetic.main.item_portfolio.view.*

class PortfolioAdapter(
    private var mData: List<PortfolioItem>,
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_portfolio, parent, false)
        return PortfolioViewHolder(view)
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        holder.bind(mData[position])

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class PortfolioViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: PortfolioItem) = with(itemView) {
            Glide.with(itemView.context).load(mData[position].image).into(itemView.item_portfolio_img);

            itemView.setOnClickListener {
                interaction?.onPortfolioItemClick(adapterPosition)
            }
        }
    }

    interface Interaction {
        fun onPortfolioItemClick(i: Int)
    }


}