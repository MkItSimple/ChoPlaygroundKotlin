package com.example.choplaygroundkotlin.cv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.choplaygroundkotlin.R
import kotlinx.android.synthetic.main.item_cv.view.*


class CVAdapter(
    private var mData: List<CVItem>
) : RecyclerView.Adapter<CVAdapter.CVViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CVViewHolder {
        val layout: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cv, parent, false)
        return CVViewHolder(layout)
    }

    override fun onBindViewHolder(holder: CVViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class CVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CVItem) = with(itemView) {
            itemView.item_cv_title.text = item.title
            itemView.item_cv_desc.text = item.description
        }
    }

}