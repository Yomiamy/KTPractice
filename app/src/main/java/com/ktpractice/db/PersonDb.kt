package com.ktpractice.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ktpractice.model.Person

@Database(entities = [Person::class], version = 1)
abstract class PersonDb: RoomDatabase() {
    abstract fun personDao(): PersonDao
}