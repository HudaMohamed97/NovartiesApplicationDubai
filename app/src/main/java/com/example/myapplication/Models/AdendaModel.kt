package com.example.myapplication.Models

import android.os.Parcel
import android.os.Parcelable

data class AdendaModel(
    var speakerName: String,
    var time: String,
    var description: String,
    var title: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(p0: Parcel?, p1: Int) {
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<AdendaModel> {
        override fun createFromParcel(parcel: Parcel): AdendaModel {
            return AdendaModel(parcel)
        }

        override fun newArray(size: Int): Array<AdendaModel?> {
            return arrayOfNulls(size)
        }
    }
}