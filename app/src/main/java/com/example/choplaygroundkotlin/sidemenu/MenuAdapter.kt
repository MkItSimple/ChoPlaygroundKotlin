package com.example.choplaygroundkotlin.sidemenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.choplaygroundkotlin.R
import kotlinx.android.synthetic.main.item_menu.view.*


class MenuAdapter(
    private var mData: List<MenuItem>,
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MenuViewHolder,
        position: Int
    ) {
        holder.bind(mData[position])
    }


    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MenuViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: MenuItem) = with(itemView) {
            itemView.item_menu_icon.setImageResource(item.icon)

            if (item.isSelected){
                itemView.item_menu_selected.visibility = View.VISIBLE
            } else {
                itemView.item_menu_selected.visibility = View.GONE
            }

            itemView.setOnClickListener {
                interaction?.onSideMenuItemClick(adapterPosition)
            }
        }
    }

    interface Interaction {
        fun onSideMenuItemClick(i: Int)
    }

}