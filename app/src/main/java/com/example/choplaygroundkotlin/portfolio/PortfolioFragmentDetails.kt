package com.example.choplaygroundkotlin.portfolio

import android.R.attr.description
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.choplaygroundkotlin.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_portfolio_details.*


class PortfolioFragmentDetails : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_portfolio_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // first we need to get our portfolio object from the bundle we have sent
        val bundle = arguments
        val item = bundle!!.getSerializable("object") as PortfolioItem

        // now we have the item we need just to load it
        loadPortfolioData(item)
    }

    private fun loadPortfolioData(item: PortfolioItem) {
        Glide.with(context!!).load(item.image).into(portfolio_details_img)
        // bind title and description ...
    }

}