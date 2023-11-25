package hu.bme.aut.szoftarch.kozkincsker

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import co.zsmb.rainbowcake.navigation.SimpleNavActivity
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.szoftarch.kozkincsker.ui.login.LoginFragment

@AndroidEntryPoint
class MainActivity : SimpleNavActivity() {
    companion object {
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1234
    }

    private val locationPermissionGranted = mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initialize(applicationContext, getString(R.string.MAPS_API_KEY))

        if (savedInstanceState == null) {
            getLocationPermission()         //Check for permission before showing anything
            navigator.add(LoginFragment())
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION

            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted.value = true//we already have the permission
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            locationPermissionGranted.value=true
        }
    }
}