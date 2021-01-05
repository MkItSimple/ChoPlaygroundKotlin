package com.example.choplaygroundkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.choplaygroundkotlin.cv.CVFragment
import com.example.choplaygroundkotlin.home.HomeFragment
import com.example.choplaygroundkotlin.portfolio.PortfolioFragment
import com.example.choplaygroundkotlin.sidemenu.MenuAdapter
import com.example.choplaygroundkotlin.sidemenu.MenuUtil
import com.example.choplaygroundkotlin.team.TeamFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MenuAdapter.Interaction {

    private lateinit var adapter: MenuAdapter
    private var selectedMenuPos = 0
    private val menuItems = MenuUtil.getMenuList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        setupSideMenu()
        setHomeFragment()
    }

    private fun setupSideMenu() {
        adapter = MenuAdapter(menuItems, this)
        rv_side_menu.layoutManager = LinearLayoutManager(this)
        rv_side_menu.adapter = adapter
    }

    private fun setPortfoliofragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container, PortfolioFragment())
            .commit()
    }

    private fun setTeamFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container, TeamFragment())
            .commit()
    }

    private fun setCVFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container, CVFragment())
            .commit()
    }

    private fun setHomeFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment())
            .commit()
    }

    override fun onSideMenuItemClick(i: Int) {
        when (menuItems[i].code) {
            MenuUtil.HOME_FRAGMENT_CODE -> setHomeFragment()
            MenuUtil.CV_FRAGMENT_CODE -> setCVFragment()
            MenuUtil.TEAM_FRAGMENT_CODE -> setTeamFragment()
            MenuUtil.PORTFOLIO_FRAGMENT_CODE -> setPortfoliofragment()
            else -> setHomeFragment()
        }

        // hightligh the selected menu item
        menuItems[selectedMenuPos].isSelected = false
        menuItems[i].isSelected = true
        selectedMenuPos = i
        adapter.notifyDataSetChanged()
    }
}