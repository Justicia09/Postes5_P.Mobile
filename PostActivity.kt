package com.uti.posttest5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText

class PostActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_POST = "extra_post"
    }

    private lateinit var etUsername: TextInputEditText
    private lateinit var etCaption: TextInputEditText
    private lateinit var tvAddImage: TextView
    private lateinit var ivPreview: ImageView
    private lateinit var btnSimpan: Button
    private lateinit var tvTitle: TextView

    private var currentImageUri: Uri? = null
    private var existingPost: Postingan? = null

    private lateinit var postinganDao: PostinganDao
    private lateinit var appExecutor: AppExecutor

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            currentImageUri = it
            ivPreview.setImageURI(it)
            ivPreview.visibility = ImageView.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val db = DatabasePostingan.getDatabase(this)
        postinganDao = db.postinganDao()
        appExecutor = AppExecutor()

        etUsername = findViewById(R.id.et_username)
        etCaption = findViewById(R.id.et_caption)
        tvAddImage = findViewById(R.id.tv_add_image)
        ivPreview = findViewById(R.id.iv_preview)
        btnSimpan = findViewById(R.id.btn_simpan)
        tvTitle = findViewById(R.id.tv_post_title)

        existingPost = intent.getParcelableExtra(EXTRA_POST)
        if (existingPost != null) {
            setupEditMode()
        } else {
            setupAddMode()
        }

        tvAddImage.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        btnSimpan.setOnClickListener {
            savePost()
        }
    }

    private fun setupEditMode() {
        tvTitle.text = "Edit Post"
        btnSimpan.text = "Update"

        existingPost?.let { post ->
            etUsername.setText(post.username)
            etCaption.setText(post.caption)
            currentImageUri = Uri.parse(post.imageUri)
            Glide.with(this)
                .load(currentImageUri)
                .into(ivPreview)
            ivPreview.visibility = ImageView.VISIBLE
        }
    }

    private fun setupAddMode() {
        tvTitle.text = "Buat Post Baru"
        btnSimpan.text = "Simpan"
    }

    private fun savePost() {
        val username = etUsername.text.toString().trim()
        val caption = etCaption.text.toString().trim()

        if (username.isEmpty() || caption.isEmpty() || currentImageUri == null) {
            Toast.makeText(this, "Isi semua kolom dulu", Toast.LENGTH_SHORT).show()
            return
        }

        val imageUriString = currentImageUri.toString()

        appExecutor.diskIO.execute {
            if (existingPost != null) {
                // Mode Update
                val updatedPost = existingPost!!.copy(
                    username = username,
                    caption = caption,
                    imageUri = imageUriString
                )
                postinganDao.update(updatedPost)
                runOnUiThread {
                    Toast.makeText(this, "Post diperbarui", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                // Mode Insert
                val newPost = Postingan(
                    username = username,
                    caption = caption,
                    imageUri = imageUriString
                )
                postinganDao.insert(newPost)
                runOnUiThread {
                    Toast.makeText(this, "Post berhasil disimpan", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
