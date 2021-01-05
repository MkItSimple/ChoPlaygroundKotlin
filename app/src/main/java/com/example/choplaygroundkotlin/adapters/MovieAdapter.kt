package com.example.choplaygroundkotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.choplaygroundkotlin.R
import com.example.choplaygroundkotlin.models.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(
    private var mData: List<Movie>,
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Movie) = with(itemView) {
            itemView.item_movie_title.text = item.title
            itemView.item_movie_img.setImageResource(item.thumbnail)

            itemView.setOnClickListener {
                interaction?.onMovieClick(item, item_movie_img)
            }
        }
    }

    interface Interaction {
        fun onMovieClick(movie: Movie, ImgMovie: ImageView)
    }
}