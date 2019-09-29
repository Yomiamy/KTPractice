package com.ktpractice.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.ktpractice.model.Person
import io.reactivex.Observable
import java.util.*

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Person)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(person: List<Person>)

    @Transaction
    fun insertBatch(personList: List<Person>) {
        personList.forEach {
            insert(it)
        }
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(person: Person)

    @Query("SELECT * FROM Person WHERE teamName = :teamName")
    fun getPersonList(teamName:String): DataSource.Factory<Int, Person>

//    @Query("SELECT * FROM `Person` WHERE teamName = :teamName")
//    fun getLiveDataPersonList(teamName:String): LiveData<List<Person>>
}