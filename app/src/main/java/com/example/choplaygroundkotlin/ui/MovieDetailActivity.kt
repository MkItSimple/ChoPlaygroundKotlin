package com.example.choplaygroundkotlin.ui

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.choplaygroundkotlin.R
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        iniViews()
    }

    private fun iniViews() {
        val movieTitle = intent.extras!!.getString("title")
        val imageResourceId = intent.extras!!.getInt("imgURL")
        val imagecover = intent.extras!!.getInt("imgCover")

        Glide.with(this).load(imageResourceId).into(detail_movie_img)
        detail_movie_img.setImageResource(imageResourceId)
        Glide.with(this).load(imagecover).into(detail_movie_cover)
        detail_movie_title.text = movieTitle
        supportActionBar!!.title = movieTitle

        // setup animation
        detail_movie_cover.animation = AnimationUtils.loadAnimation(
            this,
            R.anim.scale_animation
        )
//        play_fab.animation = AnimationUtils.loadAnimation(this,
//            R.anim.scale_animation
//        )
    }

}