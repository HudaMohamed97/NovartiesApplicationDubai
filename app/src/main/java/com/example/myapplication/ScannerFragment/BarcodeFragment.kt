package com.example.myapplication.ScannerFragment

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.google.android.gms.vision.barcode.Barcode
import com.notbytes.barcode_reader.BarcodeReaderFragment
import kotlinx.android.synthetic.main.fragment_barcode.*


class BarcodeFragment : Fragment(), BarcodeReaderFragment.BarcodeReaderListener {

    private var barcodeReader: BarcodeReaderFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_barcode, container, false)
        barcodeReader =
            childFragmentManager.findFragmentById(R.id.barcode_fragment) as BarcodeReaderFragment?
        barcodeReader!!.setListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button2.setOnClickListener {
            findNavController().navigate(
                R.id.action_HomeFragment_to_AddFragment)
        }
    }

    override fun onScanned(barcode: Barcode) {
        Log.i("hhhhh", "onScanned: " + barcode.displayValue)
        barcodeReader!!.playBeep()
        Toast.makeText(activity, "Barcode: " + barcode.rawValue, Toast.LENGTH_SHORT).show()
        var bundle = bundleOf("fromFragment" to barcode.rawValue)

        if (findNavController().currentDestination?.id == R.id.ScannerFragment) {
            findNavController().navigate(
                R.id.action_HomeFragment_to_AddFragment, bundle,
                NavOptions.Builder()
                    .setPopUpTo(
                        R.id.loginFragment,
                        true
                    ).build()
            )
        }
    }

    override fun onScannedMultiple(barcodes: List<Barcode>) {
        Log.e(TAG, "onScannedMultiple: " + barcodes.size)

        var codes = ""
        for (barcode in barcodes) {
            codes += barcode.displayValue + ", "
        }

        val finalCodes = codes
        Toast.makeText(activity, "Barcodes: $finalCodes", Toast.LENGTH_SHORT).show()
    }

    override fun onBitmapScanned(sparseArray: SparseArray<Barcode>) {

    }

    override fun onScanError(errorMessage: String) {
        Log.e(TAG, "onScanError: $errorMessage")
    }

    override fun onCameraPermissionDenied() {
        Toast.makeText(activity, "Camera permission denied!", Toast.LENGTH_LONG).show()
    }

    companion object {
        private val TAG = BarcodeFragment::class.java.simpleName

        fun newInstance(): BarcodeFragment {
            val args = Bundle()
            val fragment = BarcodeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}