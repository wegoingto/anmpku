package com.example.what.model

import android.os.Parcel
import android.os.Parcelable

data class Menu(
    val name: String,
    val img: String,
    val price: Int,
    val cat: Int,
    val desc: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(img)
        parcel.writeInt(price)
        parcel.writeInt(cat)
        parcel.writeString(desc)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Menu> {
        override fun createFromParcel(parcel: Parcel) = Menu(parcel)
        override fun newArray(size: Int): Array<Menu?> = arrayOfNulls(size)
    }
}