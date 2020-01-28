package com.example.mapdemo

import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var marker: MarkerOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        //
        marker = MarkerOptions().position(LatLng(0.0, 0.0)).title("Marker in Sydney")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.gps))
        marker.zIndex(4.6f)

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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(0.0, 0.0)
       // marker.visible(true)
        //marker.draggable(true)
//        mMap.addMarker(marker)
//        marker.position(sydney)
        //Set the current location of the user in  place of sydney
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.setOnCameraIdleListener(OnCameraIdleListener {
            //get latlng at the center by calling

            val midLatLng: LatLng = mMap.getCameraPosition().target
            val geocoder = Geocoder(this, Locale.getDefault())
            val address = geocoder.getFromLocation(midLatLng.latitude, midLatLng.longitude, 1)
            if (address.size > 0) {
                Toast.makeText(this,
                    " lat ${marker.position.latitude} lng ${marker.position.longitude} address ${address[0].locality}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
