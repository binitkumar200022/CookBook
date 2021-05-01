package com.example.cookbook.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.CategoryActivity
import com.example.cookbook.R
import com.example.cookbook.adapter.RecyclerViewAdapter.MyViewHolder
import com.example.cookbook.model.Category
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.squareup.picasso.Picasso

class RecyclerViewAdapter
    (options: FirebaseRecyclerOptions<Category>) :
    FirebaseRecyclerAdapter<Category, MyViewHolder>(options) {

    var intent: Intent? = null

    override fun onBindViewHolder(MyViewHolder: MyViewHolder, i: Int, category: Category) {
        MyViewHolder.catName.text = category.catName
        Picasso.get().load(category.imageUrl).into(MyViewHolder.catImage)
        MyViewHolder.itemView.setOnClickListener() {
            intent = Intent(MyViewHolder.itemView.context, CategoryActivity::class.java)
            intent?.putExtra("EXTRA_CAT_NAME", category.catName)
            MyViewHolder.itemView.context.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_category, parent, false)

        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var catImage: ImageView = itemView.findViewById(R.id.categoryThumb)
        var catName: TextView = itemView.findViewById(R.id.categoryName)

    }

}