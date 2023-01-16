package com.example.bintask.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.bintask.MainActivity
import com.example.bintask.R
import com.example.bintask.base.UiEvent
import com.example.bintask.base.ViewState
import com.example.bintask.network.data.models.BINInfoModel
import com.google.android.material.card.MaterialCardView
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModel()

    private val btnLoadBINInfo: Button by lazy { requireActivity().findViewById(R.id.btnLoadBINInfo) }
    private val etBIN: EditText by lazy { requireActivity().findViewById(R.id.etBIN) }
    private val btnSeePreviousRequests: Button by lazy { requireActivity().findViewById(R.id.btnSeePreviousRequests) }

    private val cvBINInfo2: MaterialCardView by lazy { requireActivity().findViewById(R.id.cvBINInfo2) }
    private var latitude: Double? = null // широта
    private var longitude: Double? = null // долгота
    private var url: String = ""

    private val tvScheme: TextView by lazy { requireActivity().findViewById(R.id.tvScheme) }
    private val tvType: TextView by lazy { requireActivity().findViewById(R.id.tvType) }
    private val tvBrand: TextView by lazy { requireActivity().findViewById(R.id.tvBrand) }
    private val tvPrepaid: TextView by lazy { requireActivity().findViewById(R.id.tvPrepaid) }
    private val tvCountry: TextView by lazy { requireActivity().findViewById(R.id.tvCountry) }
    private val tvCoordinates: TextView by lazy { requireActivity().findViewById(R.id.tvCoordinates) }
    private val tvCardNumberLength: TextView by lazy { requireActivity().findViewById(R.id.tvCardNumberLength) }
    private val tvCardNumberLuhn: TextView by lazy { requireActivity().findViewById(R.id.tvCardNumberLuhn) }
    private val tvBankNameCity: TextView by lazy { requireActivity().findViewById(R.id.tvBankNameCity) }
    private val tvUrl: TextView by lazy { requireActivity().findViewById(R.id.tvUrl) }
    private val tvPhone: TextView by lazy { requireActivity().findViewById(R.id.tvPhone) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, ::render)

        btnLoadBINInfo.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnLoadBINInfoClicked(etBIN.text.toString()))
        }

        btnSeePreviousRequests.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, RequestsFragment())
                .commit()
        }

        cvBINInfo2.setOnClickListener {
            if (latitude != null && longitude != null) {
                try {
                    val mapsIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>")
                    )
                    requireActivity().startActivity(mapsIntent)
                } catch (e: Exception) {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "Failed to open location in maps",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        tvUrl.setOnClickListener {
            if (url != "") {
                try {
                    if (!url.startsWith("http://")) url = "http://${url}"
                    val browserIntent = Intent(Intent.ACTION_VIEW)
                    browserIntent.data = Uri.parse(url)
                    requireActivity().startActivity(browserIntent)
                } catch (e: Exception) {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "Failed to open website",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        tvPhone.setOnClickListener {
            if (tvPhone.text != "?") {
                if (ContextCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED) {
                    // no permission yet, ask for it
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CALL_PHONE),
                        MainActivity.REQUEST_CALL_PHONE
                    )
                } else {
                    // already have the permission
                    doThePhoneCall()
                }
            }
        }

    }

    private fun render(viewState: ViewState) {
        if (viewState.errorOnBINInfo) {
            setFieldsInformation(null)
            latitude = null
            longitude = null
            url = ""
        } else {
            setFieldsInformation(viewState.binInfo)
        }
        etBIN.setText(viewState.BIN)
    }

    private fun setFieldsInformation(binInfo: BINInfoModel?) {
        tvScheme.text = binInfo?.scheme ?: "?"
        tvType.text = binInfo?.type ?: "?"
        tvBrand.text = binInfo?.brand ?: "?"
        tvPrepaid.text = when (binInfo?.prepaid) {
            true -> "Yes"
            false -> "No"
            null -> "?"
        }

        val countryText = if (binInfo?.country == null) "?" else
            "${binInfo.country.emoji ?: "?"} ${binInfo.country.name ?: "?"}"
        tvCountry.text = countryText

        val coordinatesText: String
        if (binInfo?.country == null) {
            coordinatesText = "?"
        } else {
            latitude = binInfo.country.latitude?.toDouble()
            longitude = binInfo.country.longitude?.toDouble()
            coordinatesText =
                "(latitude: ${binInfo.country.latitude ?: "?"}\nlongitude: ${binInfo.country.longitude ?: "?"})"
        }
        tvCoordinates.text = coordinatesText

        tvCardNumberLength.text = binInfo?.number?.length?.toString() ?: "?"
        tvCardNumberLuhn.text = when (binInfo?.number?.luhn) {
            true -> "Yes"
            false -> "No"
            null -> "?"
        }

        val bankNameCityText = if (binInfo?.bank == null) "?" else
            "${binInfo.bank.name ?: "?"}, ${binInfo.bank.city ?: "?"}"
        tvBankNameCity.text = bankNameCityText

        if (binInfo?.bank?.url != null) {
            tvUrl.text = binInfo.bank.url
            url = binInfo.bank.url
        } else {
            tvUrl.text = "?"
            url = ""
        }
        tvPhone.text = binInfo?.bank?.phone ?: "?"
    }

    fun doThePhoneCall() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            val phoneToCall = "tel:${tvPhone.text}"
            intent.data = Uri.parse(phoneToCall)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                requireActivity(),
                "Failed to make a phone call",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}