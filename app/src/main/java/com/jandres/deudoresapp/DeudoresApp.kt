package com.jandres.deudoresapp

import android.app.Application
import androidx.room.Room
import com.jandres.deudoresapp.data.DebtorDatabase
import com.jandres.deudoresapp.data.UsersDatabase


class DeudoresApp : Application(){

    companion object{
        lateinit var database: DebtorDatabase
        //lateinit var usersDatabase: UsersDatabase
    }

    override fun onCreate(){
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            DebtorDatabase::class.java,
            "debtor_db"
        ).allowMainThreadQueries()
            .build()

        /*usersDatabase = Room.databaseBuilder(
            this,
            UsersDatabase::class.java,
            "user_db"
        ).allowMainThreadQueries()
            .build()*/
    }
}

