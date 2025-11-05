package com.uti.posttest5

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostinganDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(postingan: Postingan)

    @Update
    fun update(postingan: Postingan)

    @Delete
    fun delete(postingan: Postingan)

    @Query("SELECT * FROM postingan_table ORDER BY id DESC")
    fun getAllPostingan(): LiveData<List<Postingan>>

    @Query("SELECT * FROM postingan_table WHERE id = :id")
    fun getPostinganById(id: Int): LiveData<Postingan>
}
