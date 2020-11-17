package com.example.choplaygroundkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

//    lateinit var mSearchText : EditText
//    lateinit var mRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mSearchText =findViewById(R.id.searchText)
//        mRecyclerView = findViewById(R.id.list_view)
//
//        mRecyclerView.setHasFixedSize(true)
//        mRecyclerView.setLayoutManager(LinearLayoutManager(this))
//
//        mSearchText.addTextChangedListener(object  : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {}
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                val searchText = mSearchText.getText().toString().trim()
//                Log.d("action", "onTextChanged $searchText")
//            }
//        })
    }
}