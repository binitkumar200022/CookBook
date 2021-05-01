package com.example.cookbook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.cookbook.adapter.RecyclerViewAdapter
import com.example.cookbook.adapter.ViewPagerAdapter
import com.example.cookbook.model.Category
import com.example.cookbook.model.Dish
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*


class HomeActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    private var rootRef: DatabaseReference? = null
    private var cuisinesRef: DatabaseReference? = null
    private var dishesRef: DatabaseReference? = null
    private var dishesIndianRef: DatabaseReference? = null

    private var viewPager: ViewPager? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var shimmerViewPager: View? = null

    private var recyclerView: RecyclerView? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private var shimmerRecyclerView: View? = null

    private var logOutBtn: ImageView? = null
    private var addNewRecipeBtn: TextView? = null
    private var likedRecipesBtn: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        if(mAuth!!.currentUser == null) {
            setContentView(R.layout.activity_home_skip_state)
        }
        else {
            setContentView(R.layout.activity_home_logged_state)

            logOutBtn = findViewById(R.id.log_out_btn)
            addNewRecipeBtn = findViewById(R.id.add_new_recipe)
            likedRecipesBtn = findViewById(R.id.liked_recipes)

            logOutBtn?.setOnClickListener(View.OnClickListener {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this,"Logged out Successfully!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            })

            addNewRecipeBtn?.setOnClickListener(View.OnClickListener {
                //Add new Recipe Function
            })

            likedRecipesBtn?.setOnClickListener(View.OnClickListener {
                //Add liked Recipe Function
            })
        }

        rootRef = FirebaseDatabase.getInstance().reference
        dishesRef = rootRef!!.child("dishes")
        cuisinesRef = rootRef!!.child("cuisines")
        dishesIndianRef = dishesRef!!.child("indian")

        viewPager = findViewById(R.id.viewPagerHeader)
        shimmerViewPager = findViewById(R.id.shimmerMeal)

        recyclerView = findViewById(R.id.recyclerCategory)
        shimmerRecyclerView = findViewById(R.id.shimmerCategory)

        loadViewPager()

        loadRecyclerView()

    }

    private fun loadRecyclerView() {
        val layoutManager = GridLayoutManager(
            this, 3,
            GridLayoutManager.VERTICAL, false
        )
        recyclerView?.layoutManager = layoutManager
        recyclerView?.isNestedScrollingEnabled = true

        val recyclerOptions: FirebaseRecyclerOptions<Category> =
            FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(
                    FirebaseDatabase.getInstance().reference.child("cuisines"),
                    Category::class.java
                ).build()

        recyclerViewAdapter = RecyclerViewAdapter(recyclerOptions)
        recyclerView?.adapter = recyclerViewAdapter
        recyclerViewAdapter?.startListening()
        recyclerViewAdapter!!.notifyDataSetChanged()
        shimmerRecyclerView!!.visibility = View.GONE

    }

    private fun loadViewPager() {
        dishesIndianRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
            var viewPagerList: MutableList<Dish?> = ArrayList()
            override fun onDataChange(snapshot: DataSnapshot) {
                shimmerViewPager!!.visibility = View.GONE
                for (viewPagerSnapshot in snapshot.children) {
                    viewPagerList.add(viewPagerSnapshot.getValue(Dish::class.java))
                }
                viewPagerAdapter = ViewPagerAdapter(applicationContext, viewPagerList)
                viewPager?.adapter = viewPagerAdapter
                viewPager?.setPadding(20, 0, 150, 0);
                viewPagerAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}