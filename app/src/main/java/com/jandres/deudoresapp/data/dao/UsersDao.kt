package com.jandres.deudoresapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jandres.deudoresapp.data.entities.Users

@Dao
interface UsersDao {

    @Insert
    fun createUser(user : Users)

    @Query("SELECT * FROM table_users" )
    fun getUsers() : MutableList<Users>

    @Query("SELECT * FROM table_users WHERE email LIKE :email" )
    fun searchUsers(email : String) : Users

    @Delete
    fun deleteUsers(user : Users)

}
