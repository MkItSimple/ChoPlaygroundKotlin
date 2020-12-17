package com.example.choplaygroundkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news.view.*

class NewAdapter : RecyclerView.Adapter<NewAdapter.NewsViewHolder> {

    var mContext: Context
    var mData: List<NewsItem>
    var mDataFiltered: List<NewsItem>
    private var isDark: Boolean = false

    constructor(
        mContext: Context,
        mData: List<NewsItem>,
        isDark: Boolean
    ) {
        this.mContext = mContext
        this.mData = mData
        this.isDark = isDark
        mDataFiltered = mData
    }

    constructor(
        mContext: Context,
        mData: List<NewsItem>
    ) {
        this.mContext = mContext
        this.mData = mData
        mDataFiltered = mData
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NewsViewHolder {
        val layout: View =
            LayoutInflater.from(mContext).inflate(R.layout.item_news, viewGroup, false)
        return NewsViewHolder(layout)
    }

    override fun onBindViewHolder(newsViewHolder: NewsViewHolder, position: Int) {

        // bind data here
        newsViewHolder.bind(mData[position])

        // we apply animation to views here
        // first lets create an animation for user photo
        newsViewHolder.img_user.animation = AnimationUtils.loadAnimation(
            mContext,
            R.anim.fade_transition_animation
        )

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        newsViewHolder.container.animation = AnimationUtils.loadAnimation(
            mContext,
            R.anim.fade_scale_animation
        )
        newsViewHolder.tv_title.text = mDataFiltered[position].title
        newsViewHolder.tv_content.text = mDataFiltered[position].content
        newsViewHolder.tv_date.text = mDataFiltered[position].Date
        newsViewHolder.img_user.setImageResource(mDataFiltered[position].userPhoto)
    }

    override fun getItemCount(): Int {
        return mDataFiltered.size
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val Key = constraint.toString()
                mDataFiltered = if (Key.isEmpty()) {
                    mData
                } else {
                    val lstFiltered: MutableList<NewsItem> = ArrayList()
                    for (row in mData) {
                        if (row.title.toLowerCase().contains(Key.toLowerCase())) {
                            lstFiltered.add(row)
                        }
                    }
                    lstFiltered
                }
                val filterResults = FilterResults()
                filterResults.values = mDataFiltered
                return filterResults
            }

            override fun publishResults(
                constraint: CharSequence,
                results: FilterResults
            ) {
                mDataFiltered = results.values as List<NewsItem>
                notifyDataSetChanged()
            }
        }
    }

    class NewsViewHolder
    constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

//        var tv_title: TextView? = null
//        var tv_content:TextView? = null
//        var tv_date:TextView? = null
//        var img_user: ImageView? = null
//        var container: RelativeLayout? = null
//
//
//        fun NewsViewHolder(itemView: View) {
//            container = itemView.findViewById(R.id.container)
//            tv_title = itemView.findViewById(R.id.tv_title)
//            tv_content = itemView.findViewById<TextView>(R.id.tv_description)
//            tv_date = itemView.findViewById<TextView>(R.id.tv_date)
//            img_user = itemView.findViewById(R.id.img_user)
//            if (isDark) {
//                setDarkTheme()
//            }
//        }

        val container = itemView.container
        val tv_title = itemView.tv_title
        val tv_content = itemView.tv_description
        val tv_date = itemView.tv_date
        val img_user = itemView.img_user

        init {
            setDarkTheme()
        }

        private fun setDarkTheme() {
            container.setBackgroundResource(R.drawable.card_bg_dark)
        }


        fun bind(item: NewsItem){

        }
    }



}