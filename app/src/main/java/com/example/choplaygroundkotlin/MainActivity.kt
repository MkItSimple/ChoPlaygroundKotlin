package com.example.choplaygroundkotlin

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Pair

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        constraintLayout.setOnClickListener {
            val intent = Intent(this, SharedActivity::class.java)

            val pairs = listOf<Pair<View, String>>(
                Pair.create<View, String>(profile_image, "imageTransition"),
                Pair.create<View, String>(profile_name, "nameTransition"),
                Pair.create<View, String>(profile_description, "descTransition")
            )

            val options = ActivityOptions.makeSceneTransitionAnimation(this,
                pairs[0],
                pairs[1],
                pairs[2]
            )

            startActivity(intent, options.toBundle())
        }
    }
}