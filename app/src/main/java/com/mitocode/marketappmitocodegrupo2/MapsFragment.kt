package com.mitocode.marketappmitocodegrupo2

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mitocode.marketappmitocodegrupo2.databinding.FragmentMapsBinding


class MapsFragment : Fragment(), OnMapReadyCallback {


    private lateinit var binding : FragmentMapsBinding

    private lateinit var map : GoogleMap


    @SuppressLint("MissingPermission")
    val coarsePerrmision = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
        when{
            isGranted -> map.isMyLocationEnabled = true
            else -> println("Denegado")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapsBinding.bind(view)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        createMarker()
        enableLocation()
    }

    @SuppressLint("MissingPermission")
    private fun enableLocation() {
        if(!::map.isInitialized) return

        if(isLocationPermissionGranted()){
            map.isMyLocationEnabled = true
        }else{
            coarsePerrmision.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }


    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED



    private fun createMarker(){
        //Latitud, Longitud
        val place = LatLng(-12.0897664,-77.0553024)
        val marker = MarkerOptions().position(place).title("Plaza Salaverry")
        map.addMarker(marker)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(place,16f),1000,null)
    }


}