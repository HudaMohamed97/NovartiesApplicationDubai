package com.example.myapplication.Models

import android.os.Parcel
import android.os.Parcelable
import com.example.myapplication.Models.EventModels.SpeakerSession
import com.google.gson.annotations.SerializedName

data class Sessions(

    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("desc") val desc: String,
    @SerializedName("location") val location: String,
    @SerializedName("time_from") val time_from: String,
    @SerializedName("time_to") val time_to: String,
    @SerializedName("speakers") val speakers: List<SpeakerSession>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        TODO("speakers")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeString(location)
        parcel.writeString(time_from)
        parcel.writeString(time_to)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sessions> {
        override fun createFromParcel(parcel: Parcel): Sessions {
            return Sessions(parcel)
        }

        override fun newArray(size: Int): Array<Sessions?> {
            return arrayOfNulls(size)
        }
    }
}

