package com.example.cookbook.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.cookbook.DishActivity
import com.example.cookbook.R
import com.example.cookbook.model.Dish
import com.squareup.picasso.Picasso

class ViewPagerAdapter(private var context: Context, var dishesList: MutableList<Dish?>) :
    PagerAdapter() {

    var intent: Intent? = null
    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return dishesList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = inflater.inflate(R.layout.item_view_pager_header, container, false)
        val dishThumb = view.findViewById<ImageView>(R.id.dishThumb)
        val dishName = view.findViewById<TextView>(R.id.dishName)
        Picasso.get().load(dishesList[position]?.imageUrl).into(dishThumb)
        dishName.text = dishesList[position]?.dishName
        view.setOnClickListener {
            intent = Intent(view.context, DishActivity::class.java)
            intent?.putExtra("EXTRA_UID", dishesList[position]?.uid)
            view.context.startActivity(intent)
        }
        container.addView(view)
        return view
    }

}