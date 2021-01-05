package com.example.choplaygroundkotlin.sidemenu

import com.example.choplaygroundkotlin.R


class MenuUtil {

    companion object{
        const val HOME_FRAGMENT_CODE = 0
        const val CV_FRAGMENT_CODE = 1
        const val PORTFOLIO_FRAGMENT_CODE = 2
        const val TEAM_FRAGMENT_CODE = 3

        fun getMenuList(): List<MenuItem> {
            val list: MutableList<MenuItem> =
                ArrayList()
            // first menu item is selected
            list.add(
                MenuItem(
                    R.drawable.ic_baseline_home_24,
                    HOME_FRAGMENT_CODE,
                    true
                )
            )
            list.add(
                MenuItem(
                    R.drawable.ic_baseline_school_24,
                    CV_FRAGMENT_CODE,
                    false
                )
            )
            list.add(
                MenuItem(
                    R.drawable.ic_baseline_group_24,
                    TEAM_FRAGMENT_CODE,
                    false
                )
            )
            list.add(
                MenuItem(
                    R.drawable.ic_baseline_dashboard_24,
                    PORTFOLIO_FRAGMENT_CODE,
                    false
                )
            )
            return list
        }

//        fun getMenuList(): List<MenuItem>{
//            val list = ArrayList<MenuItem>()
//            // first menu item is selected
//            list.add(
//                MenuItem(
//                    R.drawable.ic_baseline_home_24,
//                    HOME_FRAGMENT_CODE,
//                    true
//                )
//            )
//            list.add(
//                MenuItem(
//                    R.drawable.ic_baseline_school_24,
//                    CV_FRAGMENT_CODE,
//                    false
//                )
//            )
//            list.add(
//                MenuItem(
//                    R.drawable.ic_baseline_group_24,
//                    TEAM_FRAGMENT_CODE,
//                    false
//                )
//            )
//            list.add(
//                MenuItem(
//                    R.drawable.ic_baseline_dashboard_24,
//                    PORTFOLIO_FRAGMENT_CODE,
//                    false
//                )
//            )
//            return list
//        }
    }


}