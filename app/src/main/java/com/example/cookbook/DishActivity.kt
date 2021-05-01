package com.example.cookbook

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import com.example.cookbook.model.Dish
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class DishActivity : AppCompatActivity() {

    private var rootRef: DatabaseReference? = null
    private var dishesRef: DatabaseReference? = null

    private var toolbar: Toolbar? = null
    private var appBarLayout: AppBarLayout? = null
    private var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    private var dishThumb: ImageView? = null
    private var difficultyLevel: TextView? = null
    private var totalCookTime: TextView? = null
    private var instructions: TextView? = null
    private var ingredients: TextView? = null
    private var measures: TextView? = null
    private var youtube: TextView? = null
    private var source: TextView? = null
    private var currentUid: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish)

        rootRef = FirebaseDatabase.getInstance().reference
        dishesRef = rootRef!!.child("dishes")

        toolbar = findViewById(R.id.toolbar)
        appBarLayout = findViewById(R.id.appbar)
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar)
        dishThumb = findViewById(R.id.dishThumb)
        difficultyLevel = findViewById(R.id.difficultyLevel)
        totalCookTime = findViewById(R.id.totalCookTime)
        instructions = findViewById(R.id.instructions)
        ingredients = findViewById(R.id.ingredients)
        measures = findViewById(R.id.measures)
        youtube = findViewById(R.id.youtube)
        source = findViewById(R.id.source)

        currentUid = intent.getStringExtra("EXTRA_UID")

        setSupportActionBar(toolbar);
        collapsingToolbarLayout?.setContentScrimColor(resources.getColor(R.color.dark_yellow));
        collapsingToolbarLayout?.setCollapsedTitleTextColor(resources.getColor(R.color.white));
        collapsingToolbarLayout?.setExpandedTitleColor(resources.getColor(R.color.white));
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dishesRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    val catName = snapshot.key
                    if (catName != null) {
                        dishesRef!!.child(catName)
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    for (newSnapshot: DataSnapshot? in dataSnapshot.children) {
                                        val nowCat: Dish? = newSnapshot?.getValue(Dish::class.java)
                                        if (nowCat != null) {
                                            if (nowCat.uid == currentUid) {

                                                Picasso.get().load(nowCat.imageUrl).into(dishThumb)
                                                collapsingToolbarLayout?.title = nowCat.dishName
                                                difficultyLevel?.text = nowCat.difficultyLevel
                                                totalCookTime?.text = nowCat.cookTime
                                                instructions?.text = nowCat.instructions
                                                ingredients?.text = nowCat.ingredients
                                                measures?.text = nowCat.measures

                                                youtube?.setOnClickListener(View.OnClickListener {
                                                    val intentSource: Intent =
                                                        Intent(Intent.ACTION_VIEW)
                                                    intentSource.data =
                                                        Uri.parse(nowCat.youtubeUrl)
                                                    startActivity(intentSource)
                                                })
                                                source?.setOnClickListener(View.OnClickListener {
                                                    val intentSource: Intent =
                                                        Intent(Intent.ACTION_VIEW)
                                                    intentSource.data = Uri.parse(nowCat.source)
                                                    startActivity(intentSource)
                                                })
                                            }
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {

                                }
                            })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun setupColorActionBarIcon(favoriteItemColor: Drawable) {
        appBarLayout!!.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _: AppBarLayout?, verticalOffset: Int ->
            if (collapsingToolbarLayout!!.height + verticalOffset < 2 * ViewCompat.getMinimumHeight(
                    collapsingToolbarLayout!!
                )
            ) {
                if (toolbar!!.navigationIcon != null) toolbar!!.navigationIcon!!
                    .setColorFilter(
                        resources.getColor(R.color.black),
                        PorterDuff.Mode.SRC_ATOP
                    )
                favoriteItemColor.mutate().setColorFilter(
                    resources.getColor(R.color.red),
                    PorterDuff.Mode.SRC_ATOP
                )
            } else {
                if (toolbar!!.navigationIcon != null) toolbar!!.navigationIcon!!
                    .setColorFilter(
                        resources.getColor(R.color.black),
                        PorterDuff.Mode.SRC_ATOP
                    )
                favoriteItemColor.mutate().setColorFilter(
                    resources.getColor(R.color.red),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        val favoriteItem = menu?.findItem(R.id.favorite)
        val favoriteItemColor = favoriteItem?.icon
        if (favoriteItemColor != null) {
            setupColorActionBarIcon(favoriteItemColor)
        };
        return true
    }

}