package com.example.cookbook

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.cookbook.adapter.ViewPagerCategoryAdapter
import com.example.cookbook.model.Category
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*


class CategoryActivity : AppCompatActivity() {

    private var rootRef: DatabaseReference? = null
    private var cuisinesRef: DatabaseReference? = null

    private var toolbar: Toolbar? = null
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private var currentCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        rootRef = FirebaseDatabase.getInstance().reference
        cuisinesRef = rootRef!!.child("cuisines")

        toolbar = findViewById(R.id.toolbar)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar!!.navigationIcon!!
            .setColorFilter(
                resources.getColor(R.color.black),
                PorterDuff.Mode.SRC_ATOP
            )

        currentCategory = intent.getStringExtra("EXTRA_CAT_NAME")

        val categories: MutableList<Category> = mutableListOf()
        var position: Int = 0

        cuisinesRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot: DataSnapshot? in dataSnapshot.children) {
                    val nowCat: Category? = snapshot?.getValue(Category::class.java)
                    if (nowCat != null) {
                        categories.add(nowCat)
                        if (nowCat.catName == currentCategory) {
                            position = categories.count()
                            position -= 1;
                        }
                    }

                    val adapter = ViewPagerCategoryAdapter(
                        supportFragmentManager,
                        categories
                    )
                    viewPager?.adapter = adapter
                    tabLayout?.setupWithViewPager(viewPager)
                    viewPager?.setCurrentItem(position, true)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}