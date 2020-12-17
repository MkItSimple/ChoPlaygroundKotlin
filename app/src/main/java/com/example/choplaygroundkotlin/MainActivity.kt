package com.example.choplaygroundkotlin

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var newsAdapter: NewAdapter
    private lateinit var mData: ArrayList<NewsItem>
    private var isDark = false

    var search: CharSequence = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

//        val pref: SharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)


        supportActionBar!!.hide()
        mData = ArrayList()


        // fill list news with data
        // just for testing purpose i will fill the news list with random data
        // you may get your data from an api / firebase or sqlite database ...

        // fill list news with data
        // just for testing purpose i will fill the news list with random data
        // you may get your data from an api / firebase or sqlite database ...
        mData.add(
            NewsItem(
                "OnePlus 6T Camera Review:",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                "6 july 1994",
                R.drawable.user
            )
        )
        mData.add(
            NewsItem(
                "I love Programming And Design",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
                "6 july 1994",
                R.drawable.circul6
            )
        )
        mData.add(
            NewsItem(
                "My first trip to Thailand story ",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                "6 july 1994",
                R.drawable.uservoyager
            )
        )
        mData.add(
            NewsItem(
                "After Facebook Messenger, Viber now gets...",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,",
                "6 july 1994",
                R.drawable.useillust
            )
        )
        mData.add(
            NewsItem(
                "Isometric Design Grid Concept",
                "lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit",
                "6 july 1994",
                R.drawable.circul6
            )
        )
        mData.add(
            NewsItem(
                "Android R Design Concept 4K",
                "lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit ",
                "6 july 1994",
                R.drawable.user
            )
        )
        mData.add(
            NewsItem(
                "OnePlus 6T Camera Review:",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                "6 july 1994",
                R.drawable.user
            )
        )
        mData.add(
            NewsItem(
                "I love Programming And Design",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
                "6 july 1994",
                R.drawable.circul6
            )
        )
        mData.add(
            NewsItem(
                "My first trip to Thailand story ",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                "6 july 1994",
                R.drawable.uservoyager
            )
        )
        mData.add(
            NewsItem(
                "After Facebook Messenger, Viber now gets...",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,",
                "6 july 1994",
                R.drawable.useillust
            )
        )
        mData.add(
            NewsItem(
                "Isometric Design Grid Concept",
                "lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit",
                "6 july 1994",
                R.drawable.circul6
            )
        )
        mData.add(
            NewsItem(
                "Android R Design Concept 4K",
                "lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit ",
                "6 july 1994",
                R.drawable.user
            )
        )
        mData.add(
            NewsItem(
                "OnePlus 6T Camera Review:",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                "6 july 1994",
                R.drawable.user
            )
        )
        mData.add(
            NewsItem(
                "I love Programming And Design",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
                "6 july 1994",
                R.drawable.circul6
            )
        )
        mData.add(
            NewsItem(
                "My first trip to Thailand story ",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                "6 july 1994",
                R.drawable.uservoyager
            )
        )
        mData.add(
            NewsItem(
                "After Facebook Messenger, Viber now gets...",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,",
                "6 july 1994",
                R.drawable.useillust
            )
        )
        mData.add(
            NewsItem(
                "Isometric Design Grid Concept",
                "lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit",
                "6 july 1994",
                R.drawable.circul6
            )
        )
        mData.add(
            NewsItem(
                "Android R Design Concept 4K",
                "lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit ",
                "6 july 1994",
                R.drawable.user
            )
        )

        news_rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            newsAdapter = NewAdapter(this@MainActivity, mData, isDark)
            adapter = newsAdapter
            saveThemeStatePref(isDark);
        }

        // load theme state
        isDark = getThemeStatePref()
        if (isDark) {
            // dark theme is on
            search_input.setBackgroundResource(R.drawable.search_input_dark_style)
            root_layout.setBackgroundColor(resources.getColor(R.color.black))
        } else {
            // light theme is on
            search_input.setBackgroundResource(R.drawable.search_input_style)
            root_layout.setBackgroundColor(resources.getColor(R.color.white))
        }

        fab_switcher.setOnClickListener {
            isDark = !isDark
            if (isDark){
                root_layout.setBackgroundColor(resources.getColor(R.color.black));
                search_input.setBackgroundResource(R.drawable.search_input_dark_style);
            }else {
                root_layout.setBackgroundColor(getResources().getColor(R.color.white));
                search_input.setBackgroundResource(R.drawable.search_input_style);
            }

            if (search.toString().isNotEmpty()){
                newsAdapter.getFilter().filter(search)
            }

            news_rv.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                newsAdapter = NewAdapter(this@MainActivity, mData, isDark)
                adapter = newsAdapter
                saveThemeStatePref(isDark);
            }
        }

        search_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                newsAdapter.getFilter().filter(s)
                search = s
            }

            override fun afterTextChanged(s: Editable) {}
        })


    }

    private fun saveThemeStatePref(isDark: Boolean) {

        val pref = applicationContext.getSharedPreferences(
            "myPref",
            Context.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putBoolean("isDark", isDark)
        editor.apply()
    }

    private fun getThemeStatePref(): Boolean {
        val pref = applicationContext.getSharedPreferences(
            "myPref",
            Context.MODE_PRIVATE
        )
        return pref.getBoolean("isDark", false)
    }
}