package com.jandres.deudoresapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jandres.deudoresapp.data.dao.UsersDao
import com.jandres.deudoresapp.data.entities.Users

@Database(entities = [Users::class], version = 1)
abstract class UsersDatabase: RoomDatabase() {

    abstract fun UsersDao() : UsersDao
}

