package com.example.choplaygroundkotlin.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.choplaygroundkotlin.R
import kotlinx.android.synthetic.main.item_team.view.*

class TeamAdapter(
    private var mData: List<TeamItem>
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val layout: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(layout)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TeamItem) = with(itemView) {
            itemView.team_item_name.text = item.name
            itemView.team_item_desc.text = item.desc
            itemView.team_item_img.setImageResource(item.img)
        }
    }
}