package com.jandres.deudoresapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jandres.deudoresapp.data.dao.DebtorDao
import com.jandres.deudoresapp.data.dao.UsersDao
import com.jandres.deudoresapp.data.entities.Debtor
import com.jandres.deudoresapp.data.entities.Users

@Database(entities = [Debtor::class, Users::class], version = 1)
abstract class DebtorDatabase: RoomDatabase() {

    abstract fun DebtorDao() : DebtorDao
    abstract fun UsersDao() : UsersDao
}