package com.imformation_checker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.imformation_checker.model.Person

@Database(entities = [Person::class], version = 2)
abstract class PersonDb: RoomDatabase() {
    abstract fun personDao(): PersonDao
}

val MIGRATION_1_2:Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("alter table Person add column address TEXT default ''")
    }
}