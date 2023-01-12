package com.example.jdm_app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jdm_app.domain.Customer

@Dao
interface CustomerDao {

    @Query("SELECT * FROM customer")
    fun getCustomer(): Customer

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(customer: Customer)

    @Update
    fun update(customer: Customer)

    @Delete
    fun delete(customer: Customer)

}
