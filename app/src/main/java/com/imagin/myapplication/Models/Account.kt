package com.imagin.myapplication.Models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Account(

    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "name",
    @SerializedName("email") val email: String = "email",
    @SerializedName("active") val active: Boolean = false,
    @SerializedName("type") val type: Int = 1,
    @SerializedName("bio") val bio: String = "bio",
    @SerializedName("photo") val photo: String = ""
) : Parcelable {
    override fun writeToParcel(p0: Parcel?, p1: Int) {
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    companion object CREATOR : Parcelable.Creator<Account> {
        override fun createFromParcel(parcel: Parcel): Account {
            return Account(parcel)
        }

        override fun newArray(size: Int): Array<Account?> {
            return arrayOfNulls(size)
        }
    }
}