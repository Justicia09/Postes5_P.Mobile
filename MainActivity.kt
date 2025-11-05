package com.uti.posttest5

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var rvStories: RecyclerView
    private lateinit var rvPostingan: RecyclerView
    private lateinit var postinganAdapter: PostinganAdapter
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var postinganDao: PostinganDao
    private lateinit var appExecutor: AppExecutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DatabasePostingan.getDatabase(this)
        postinganDao = db.postinganDao()
        appExecutor = AppExecutor()

        rvStories = findViewById(R.id.rv_stories)
        rvStories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        storyAdapter = StoryAdapter(getDummyStories())
        rvStories.adapter = storyAdapter

        postinganAdapter = PostinganAdapter(
            onEdit = { postingan ->
                val intent = Intent(this, PostActivity::class.java)
                intent.putExtra(PostActivity.EXTRA_POST, postingan)
                startActivity(intent)
            },
            onDelete = { postingan ->
                deletePostingan(postingan)
            }
        )

        rvPostingan = findViewById(R.id.rv_postingan)
        rvPostingan.adapter = postinganAdapter
        rvPostingan.layoutManager = LinearLayoutManager(this)
        rvPostingan.isNestedScrollingEnabled = false

        // Observe LiveData
        postinganDao.getAllPostingan().observe(this, Observer { postinganList ->
            postinganList?.let {
                postinganAdapter.setData(it)
            }
        })

        fabAdd = findViewById(R.id.fab_add)
        fabAdd.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun deletePostingan(postingan: Postingan) {
        appExecutor.diskIO.execute {
            postinganDao.delete(postingan)

            runOnUiThread {
                Toast.makeText(this, "Postingan ${postingan.username} dihapus", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDummyStories(): List<Story> {
        return listOf(
            Story("hana", R.drawable.person_24),
            Story("jaka", R.drawable.person_24),
            Story("hasna", R.drawable.person_24),
            Story("banu", R.drawable.person_24),
            Story("zeko", R.drawable.person_24),
            Story("Dapa", R.drawable.person_24),
            Story("rere", R.drawable.person_24)
        )
    }
}
