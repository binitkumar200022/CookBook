package com.example.cookbook

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.adapter.RecyclerViewDishesAdapter
import com.example.cookbook.model.Dish
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class CategoryFragment : Fragment() {
    private var rootRef: DatabaseReference? = null
    private var dishesRef: DatabaseReference? = null
    private var recyclerView: RecyclerView? = null
    private var cardView: CardView? = null
    private var imageCategory: ImageView? = null
    private var imageCategoryBg: ImageView? = null
    private var textCategory: TextView? = null
    private var category: String? = null
    private var description: String? = null
    private var imageUrl: String? = null
    private var recyclerViewDishesAdapter: RecyclerViewDishesAdapter? = null
    var descDialog: AlertDialog.Builder? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        cardView = view.findViewById(R.id.cardCategory)
        imageCategory = view.findViewById(R.id.imageCategory)
        imageCategoryBg = view.findViewById(R.id.imageCategoryBg)
        textCategory = view.findViewById(R.id.textCategory)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootRef = FirebaseDatabase.getInstance().reference
        dishesRef = rootRef!!.child("dishes")
        if (arguments != null) {
            category = arguments!!.getString("EXTRA_FRAG_NAME")
            description = arguments!!.getString("EXTRA_FRAG_DESC")
            imageUrl = arguments!!.getString("EXTRA_FRAG_IMAGE")
            textCategory!!.text = description
            Picasso.get()
                .load(imageUrl)
                .into(imageCategory)
            Picasso.get()
                .load(imageUrl)
                .into(imageCategoryBg)
            descDialog = AlertDialog.Builder(activity)
                .setTitle(category)
                .setMessage(description)
            recyclerView!!.layoutManager = GridLayoutManager(activity, 2)
            recyclerView!!.clipToPadding = false
            val options = FirebaseRecyclerOptions.Builder<Dish>().setQuery(
                FirebaseDatabase.getInstance().reference.child("dishes").child(
                    category!!.toLowerCase()
                ), Dish::class.java
            ).build()
            recyclerViewDishesAdapter = RecyclerViewDishesAdapter(options)
            recyclerView!!.adapter = recyclerViewDishesAdapter
            recyclerViewDishesAdapter!!.startListening()
            recyclerViewDishesAdapter!!.notifyDataSetChanged()
        }
        cardView!!.setOnClickListener {
            descDialog!!.setPositiveButton("CLOSE") { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            descDialog!!.show()
        }
    }
}