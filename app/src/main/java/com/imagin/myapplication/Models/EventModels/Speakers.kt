package com.imagin.myapplication.Models.EventModels

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Speakers(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: Int,
    @SerializedName("email") val email: String,
    @SerializedName("bio") val bio: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("type") val type: String,
    @SerializedName("active") val active: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(phone)
        parcel.writeString(email)
        parcel.writeString(bio)
        parcel.writeString(photo)
        parcel.writeString(type)
        parcel.writeByte(if (active) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Speakers> {
        override fun createFromParcel(parcel: Parcel): Speakers {
            return Speakers(parcel)
        }

        override fun newArray(size: Int): Array<Speakers?> {
            return arrayOfNulls(size)
        }
    }

}
