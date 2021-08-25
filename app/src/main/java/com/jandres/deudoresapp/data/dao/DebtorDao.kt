package com.jandres.deudoresapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jandres.deudoresapp.data.entities.Debtor
import com.jandres.deudoresapp.data.entities.Users

@Dao
interface DebtorDao {

    @Insert
    fun createDebtor(deptor : Debtor)

    @Query("SELECT * FROM table_debtor" )
    fun getDebtors() : MutableList<Debtor>

    @Query("SELECT * FROM table_debtor WHERE name LIKE :name" )
    fun searchDebtor(name : String) : Debtor

    @Delete
    fun deleteDebtor(debtor : Debtor)


}