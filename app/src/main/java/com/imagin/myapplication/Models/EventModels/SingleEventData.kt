package com.imagin.myapplication.Models.EventModels

import com.google.gson.annotations.SerializedName


data class SingleEventData(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("date") val date: String,
    @SerializedName("desc") val desc: String,
    @SerializedName("contact_phone") val contact_phone: Int,
    @SerializedName("contact_email") val contact_email: String,
    @SerializedName("address") val address: String,
    @SerializedName("have_ticket") val have_ticket: Boolean,
    @SerializedName("active") val active: Boolean,
    @SerializedName("is_public") val is_public: Boolean,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("speakers") val speakers: List<Speakers>,
    @SerializedName("partnerships") val partnerships: List<String>,
    @SerializedName("sponsors") val sponsors: List<String>,
    @SerializedName("testimonials") val testimonials: List<String>,
    @SerializedName("logo") val logo: String,
    @SerializedName("cover") val cover: String
)