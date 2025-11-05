package com.uti.posttest5

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "postingan_table")
data class Postingan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val caption: String,
    val imageUri: String
) : Parcelable
