package com.example.cookbook.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.cookbook.CategoryFragment
import com.example.cookbook.model.Category

class ViewPagerCategoryAdapter(fm: FragmentManager?, private val categories: List<Category>) :
    FragmentPagerAdapter(
        fm!!
    ) {
    override fun getItem(i: Int): Fragment {
        val fragment = CategoryFragment()
        val args = Bundle()
        args.putString("EXTRA_FRAG_NAME", categories[i].catName)
        args.putString("EXTRA_FRAG_DESC", categories[i].description)
        args.putString("EXTRA_FRAG_IMAGE", categories[i].imageUrl)
        fragment.arguments = args
        return fragment
    }

    override fun getCount(): Int {
        return categories.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return categories[position].catName
    }
}