package com.example.cookbook

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class LoginAdapter(fm: FragmentManager, private val context: Context, var totalTabs: Int) :
    FragmentPagerAdapter(
        fm
    ) {

    // tab titles
    private val tabTitles = arrayOf("Login", "SignUp")

    // overriding getPageTitle()
    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position];
    }

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                LoginTabFragment()
            }
            1 -> {
                SignUpTabFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}