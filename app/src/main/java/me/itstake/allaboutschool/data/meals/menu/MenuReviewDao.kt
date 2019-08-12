package me.itstake.allaboutschool.data.meals.menu

import androidx.room.*

@Dao
interface MenuReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReview(vararg review: MenuReview)

    @Delete
    fun deleteReview(vararg review: MenuReview)

    @Query("SELECT * FROM menu_review")
    fun getAll(): List<MenuReview>

    /*
    @Query("SELECT * FROM menu_review WHERE review IN 1")
    fun getPositiveReviews()

    @Query("SELECT * FROM menu_review WHERE review IN 2")
    fun getNegativeReviews()

    @Query("SELECT * FROM menu_review WHERE review IN 0")
    fun getNaturalReviews()
    */

    @Query("DELETE FROM meals")
    fun deleteAll()
}