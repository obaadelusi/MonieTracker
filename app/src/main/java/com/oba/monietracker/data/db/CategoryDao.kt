package com.oba.monietracker.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oba.monietracker.data.models.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCategories(categories: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneCategory (category: Category?)

    @Query("SELECT * FROM tbl_categories WHERE name = :name LIMIT 1")
    fun getCategoryByName(name: String): Category

    @Query("SELECT * FROM tbl_categories")
    fun getAllCategories(): List<Category>

    @Delete
    fun deleteCategory(category: Category)

    @Query("DELETE FROM tbl_categories")
    fun deleteAllCategories()

    //@Query("DELETE FROM tbl_categories WHERE id = :id")
    //fun deleteCategoryById(id: String)
}