package com.manuelcarvalho.celltower.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.telephony.CellSignalStrength
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.manuelcarvalho.celltower.R
import com.manuelcarvalho.cocopic.viewmodel.AppViewModel

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: AppViewModel

    private val LOCATION_PERMISSION_CODE = 101
    private val FINE_LOCATION_PERMISSION_CODE = 102

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel = ViewModelProviders.of(this)[AppViewModel::class.java]

        checkPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                FINE_LOCATION_PERMISSION_CODE
        )

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        phoneStatus()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    Log.d(TAG, "Loc ${location}")
                }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission)
                == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf(permission),
                    requestCode
            )
        } else {
            Toast.makeText(
                    this@MainActivity,
                    "Permission already granted",
                    Toast.LENGTH_SHORT
            )
                    .show()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == FINE_LOCATION_PERMISSION_CODE) {
            if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                        this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT
                )
                        .show()
            } else {
                Toast.makeText(
                        this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT
                )
                        .show()
            }
        }
    }

    fun phoneStatus() {
        var tm = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        } else {
            getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager


        }

        var b = tm.allCellInfo

//        var IMEINumber = tm.deviceId
//        var subscriberID = tm.deviceId
//        var SIMSerialNumber = tm.simSerialNumber
//        var networkCountryISO = tm.networkCountryIso
//        var SIMCountryISO = tm.simCountryIso
//        var softwareVersion = tm.deviceSoftwareVersion
//        var voiceMailNumber = tm.voiceMailNumber

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d(TAG, "PhoneStatus method")
            val a = tm.phoneType
            val b = tm.networkType
            var c: SignalStrength? = null
            var d = ""
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                c = tm.signalStrength
            } else {

            }

            var e: List<CellSignalStrength>? = null
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                e = c?.cellSignalStrengths
                viewModel.details.value = e
            } else {
                TODO("VERSION.SDK_INT < Q")
            }
            Log.d(TAG, "signalStrength  ${c.toString()}")

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                d = tm.typeAllocationCode.toString()
            }

            Log.d(TAG, "getTypeAllocationCode   $b")
            return
        }
        // tm.allCellInfo
    }

}

