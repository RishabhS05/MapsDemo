package com.example.mapdemo

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_maps.*
import pub.devrel.easypermissions.EasyPermissions
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        if (EasyPermissions.hasPermissions(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Location Permission Required",
                1000,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        // marker.visible(true)
        //marker.draggable(true)
//        mMap.addMarker(marker)
//        marker.position(sydney)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location!!.latitude, location!!.longitude)))
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f))
            }
        //Set the current location of the user in  place of sydney
        mMap.setOnCameraIdleListener(OnCameraIdleListener {
            //get latlng at the center by calling

            val midLatLng: LatLng = mMap.getCameraPosition().target
            val geocoder = Geocoder(this, Locale.getDefault())
            val address = geocoder.getFromLocation(midLatLng.latitude, midLatLng.longitude, 100)
            if (address.size > 0) {
                Log.d("Address :: ", address.toString())
                Snackbar.make(
                    rlMap,
                    "${address[0].getAddressLine(0)}",
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            }
        })
    }
}
