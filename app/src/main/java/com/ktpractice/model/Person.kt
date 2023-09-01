package com.ktpractice.model

import android.os.Parcel
import android.os.Parcelable
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
    var teamName:String?,
    var address:String?
) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(rowId)
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeString(name)
        parcel.writeString(position)
        parcel.writeStringList(expertise)
        parcel.writeString(avatar)
        parcel.writeString(url)
        parcel.writeString(teamName)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }
}