package com.example.cookbook.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.DishActivity
import com.example.cookbook.R
import com.example.cookbook.adapter.RecyclerViewDishesAdapter.MyViewHolder2
import com.example.cookbook.model.Dish
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.squareup.picasso.Picasso

class RecyclerViewDishesAdapter(options: FirebaseRecyclerOptions<Dish?>) :
    FirebaseRecyclerAdapter<Dish, MyViewHolder2>(options) {

    var intent: Intent? = null

    override fun onBindViewHolder(MyViewHolder2: MyViewHolder2, i: Int, dish: Dish) {
        MyViewHolder2.catName.text = dish.dishName
        Picasso.get().load(dish.imageUrl).into(MyViewHolder2.catImg)

        MyViewHolder2.itemView.setOnClickListener() {
            intent = Intent(MyViewHolder2.itemView.context, DishActivity::class.java)
            intent?.putExtra("EXTRA_UID", dish.uid)
            MyViewHolder2.itemView.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_dish, parent, false)
        return MyViewHolder2(view)
    }

    inner class MyViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var catImg: ImageView = itemView.findViewById(R.id.dishThumb)
        var catName: TextView = itemView.findViewById(R.id.dishName)

    }
}