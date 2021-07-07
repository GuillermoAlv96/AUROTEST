package Activities.Driver.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.auotravels.R
import com.example.auotravels.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vmadalin.easypermissions.EasyPermissions
import kotlinx.android.synthetic.main.activity_maps.*
import utils.Constants.PERMISSION_LOCATION_REQUEST_CODE

class DriverMapsActivity : AppCompatActivity(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Initialize fused Location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //Request permission
        requestLocationPermissions()

        buttonGetLocation.setOnClickListener {
            getCurrentLocation()
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        // Set the map type to Normal.
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Display traffic.
        googleMap.setTrafficEnabled(true);


    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            val geoCoder = Geocoder(this)
            val currentLocation = geoCoder.getFromLocation(
                it.latitude,
                it.longitude,
                1
            )
            val currentLocatio = LatLng(it.latitude, it.longitude)
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(currentLocatio).title("YOU"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocatio))

        }
    }

    private fun requestLocationPermissions() {
        EasyPermissions.requestPermissions(
            this,
            "This application cannot work without Location Permissions.",
            PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms.first())) {

        } else {
            requestLocationPermissions()
        }
        Toast.makeText(
            baseContext,
            "Permision Dinied",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(
            baseContext,
            "Permision Granted",
            Toast.LENGTH_SHORT
        ).show()
    }
}