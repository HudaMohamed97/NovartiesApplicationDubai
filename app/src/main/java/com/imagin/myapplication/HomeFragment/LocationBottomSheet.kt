package com.imagin.myapplication.HomeFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imagin.myapplication.Models.LocationResponse
import com.imagin.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.location_bottom_sheet.*
import java.util.*

class LocationBottomSheet(private var model: LocationResponse) :
    BottomSheetDialogFragment() {
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.location_bottom_sheet, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Company_Name.text = "CompanyName : " + model.data.company_name
        event_name.text = "EventName : " + model.data.event_name
        Address.text = "Address:" + model.data.address
        cityName.text = "City : " + model.data.address

        show_location.setOnClickListener {
            var lat = model.data.lat
            var long = model.data.lng
            val uri = String.format(
                Locale.ENGLISH,
                "http://maps.google.com/maps?q=loc:%f,%f",
                lat,
                long
            )
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            dismiss()
            startActivity(intent)

        }
    }


}


