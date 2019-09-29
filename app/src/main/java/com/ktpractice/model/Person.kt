package com.ktpractice.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.*
import com.ktpractice.db.PersonTypeConvert

@Entity(indices = [Index(value = ["rowId"])])
@TypeConverters(PersonTypeConvert::class)
data class Person(
    @PrimaryKey(autoGenerate = true)
    var rowId: Int,
    var id: String?,
    var type: String?,
    var name: String?,
    var position: String?,
    var expertise: List<String>?,
    var avatar: String?,
    var url: String?,
    var teamName:String
) {
    companion object {
        val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Person>() {
            // Person details may have changed , but ID is fixed.
            override fun areItemsTheSame(
                oldPerson: Person,
                newPerson: Person
            ) = (oldPerson.id == newPerson.id)

            override fun areContentsTheSame(
                oldPerson: Person,
                newPerson: Person
            ) = oldPerson == newPerson
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false

        val otherPerson = other as Person
        return this.id == otherPerson.id
                && this.type == otherPerson.type
                && this.name == otherPerson.name
                && this.position == otherPerson.position
                && (this.expertise?.size == otherPerson.expertise?.size && this.expertise!!.containsAll(
            otherPerson.expertise!!
        ))
                && this.avatar == otherPerson.avatar
                && this.url == otherPerson.url
    }
}