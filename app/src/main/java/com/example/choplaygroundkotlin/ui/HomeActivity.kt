package com.example.choplaygroundkotlin.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.choplaygroundkotlin.R
import com.example.choplaygroundkotlin.adapters.MovieAdapter
import com.example.choplaygroundkotlin.adapters.SliderPagerAdapter
import com.example.choplaygroundkotlin.models.Movie
import com.example.choplaygroundkotlin.models.Slide
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), MovieAdapter.Interaction {

    private val lstSlides = ArrayList<Slide>()
    private val lstMovies = ArrayList<Movie>()
    private lateinit var sliderPagerAdapter: SliderPagerAdapter
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        lstSlides.add(Slide(R.drawable.slide1, "Slide Title \nmore text here"))
        lstSlides.add(Slide(R.drawable.slide2, "Slide Title \nmore text here"))
        lstSlides.add(Slide(R.drawable.slide1, "Slide Title \nmore text here"))
        lstSlides.add(Slide(R.drawable.slide2, "Slide Title \nmore text here"))

        sliderPagerAdapter = SliderPagerAdapter(this, lstSlides)
        slider_pager.adapter = sliderPagerAdapter

//        val timer = Timer()
//        timer.scheduleAtFixedRate(SliderTimer(), 4000, 6000)
//        indicator.setupWithViewPager(slider_pager, true)

        lstMovies.add(Movie("Moana", R.drawable.moana, R.drawable.spidercover))
        lstMovies.add(Movie("Black P", R.drawable.blackp, R.drawable.spidercover))
        lstMovies.add(Movie("The Martian", R.drawable.themartian))
        lstMovies.add(Movie("The Martian", R.drawable.themartian))
        lstMovies.add(Movie("The Martian", R.drawable.themartian))
        lstMovies.add(Movie("The Martian", R.drawable.themartian))

        movieAdapter = MovieAdapter(lstMovies, this)
        Rv_movies.adapter = movieAdapter
        Rv_movies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onMovieClick(movie: Movie, movieImageView: ImageView) {
// here we send movie information to detail activity
        // also we ll create the transition animation between the two activity


        // here we send movie information to detail activity
        // also we ll create the transition animation between the two activity
        val intent = Intent(this, MovieDetailActivity::class.java)
        // send movie information to deatilActivity
        // send movie information to deatilActivity
        intent.putExtra("title", movie.title)
        intent.putExtra("imgURL", movie.thumbnail)
        intent.putExtra("imgCover", movie.coverPhoto)
        // lets crezte the animation
        // lets crezte the animation
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this@HomeActivity,
            movieImageView, "sharedName"
        )

        startActivity(intent, options.toBundle())


        // i l make a simple test to see if the click works


        // i l make a simple test to see if the click works
        Toast.makeText(this, "item clicked : " + movie.title, Toast.LENGTH_LONG).show()
        // it works great
    }

//    internal class SliderTimer : TimerTask() {
//        override fun run() {
//            HomeActivity.runOnUiThread(Runnable {
//                if (sliderpager.getCurrentItem() < lstSlides.size - 1) {
//                    sliderpager.setCurrentItem(sliderpager.getCurrentItem() + 1)
//                } else sliderpager.setCurrentItem(0)
//            })
//        }
//    }
}