package com.deviceinfosample.fragment

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohit.deviceinfo.ads.Ad
import com.mohit.deviceinfo.ads.AdInfo
import com.mohit.deviceinfo.ads.AdInfo.AdIdCallback
import com.mohit.deviceinfo.device.model.*
import com.mohit.deviceinfo.location.LocationInfo
import com.mohit.deviceinfo.permission.PermissionManager
import com.mohit.deviceinfo.permission.PermissionManager.PermissionCallback
import com.mohit.deviceinfo.permission.PermissionUtils
import com.mohit.deviceinfo.userapps.UserAppInfo
import com.mohit.deviceinfo.usercontacts.UserContactInfo
import com.deviceinfosample.R
import com.deviceinfosample.adapter.CustomListAdapter

class MainFragment : Fragment(), AdIdCallback, PermissionCallback {
    private var position = 0
    private lateinit var mActivity: Activity
    private var recyclerView: RecyclerView? = null
    private var adapter: CustomListAdapter? = null
    private var permissionManager: PermissionManager? = null
    private var permissionUtils: PermissionUtils? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            position = requireArguments().getInt("pos")
        }
        permissionManager = PermissionManager(this)
        permissionUtils = PermissionUtils(mActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(mActivity)
        recyclerView!!.smoothScrollToPosition(0)
        return view
    }

    override fun onResume() {
        super.onResume()
        askPermission()
    }

    private fun askPermission() {
        var permission: String? = null
        when (position) {
            0 -> permission = Manifest.permission.ACCESS_FINE_LOCATION
            6 -> permission = Manifest.permission.READ_PHONE_STATE
            8 -> permission = Manifest.permission.READ_CONTACTS
        }
        if (permission != null) getPermission(permission) else initialize()
    }

    private fun getPermission(permission: String) {
        val permissionUtils = PermissionUtils(mActivity)
        if (!permissionUtils.isPermissionGranted(permission)) {
            permissionManager!!.showPermissionDialog(permission)
                .withCallback(this)
                .withDenyDialogEnabled(true)
                .withDenyDialogMsg(mActivity!!.getString(R.string.permission_location))
                .build()
        } else initialize()
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private fun initialize() {
        Handler().post {
            when (position) {
                0 -> {
                    val locationInfo = LocationInfo(mActivity)
                    val location = locationInfo.location
                    adapter = CustomListAdapter(mActivity!!, location)
                }
                1 -> {
                    val app = App(mActivity)
                    adapter = CustomListAdapter(mActivity!!, app)
                }
                2 -> {
                    val adInfo = AdInfo(mActivity)
                    adInfo.getAndroidAdId(this@MainFragment)
                }
                3 -> {
                    val battery = Battery(mActivity)
                    adapter = CustomListAdapter(mActivity!!, battery)
                }
                4 -> {
                    val device = Device(mActivity)
                    adapter = CustomListAdapter(mActivity!!, device)
                }
                5 -> {
                    val memory = Memory(mActivity)
                    adapter = CustomListAdapter(mActivity!!, memory)
                }
                6 -> {
                    val network = Network(mActivity)
                    adapter = CustomListAdapter(mActivity!!, network)
                }
                7 -> {
                    val userAppInfo = UserAppInfo(mActivity)
                    val userApps = userAppInfo.getInstalledApps(true)
                    adapter = CustomListAdapter(mActivity!!, userApps)
                }
                8 -> {
                    val userContactInfo = UserContactInfo(mActivity)
                    val userContacts = userContactInfo.contacts
                    adapter = CustomListAdapter(mActivity!!, userContacts)
                }
            }
            mActivity!!.runOnUiThread {
                if (adapter != null) {
                    recyclerView!!.adapter = adapter
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager!!.handleResult(requestCode, permissions, grantResults)
    }

    override fun onPermissionGranted(permissions: Array<String>, grantResults: IntArray) {
        initialize()
    }

    override fun onPermissionDismissed(permission: String) {}
    override fun onPositiveButtonClicked(dialog: DialogInterface, which: Int) {
        /**
         * You can choose to open the
         * app settings screen
         * *  */
        permissionUtils!!.openAppSettings()
        Toast.makeText(mActivity, "User has opened the settings screen", Toast.LENGTH_LONG).show()
    }

    override fun onNegativeButtonClicked(dialog: DialogInterface, which: Int) {
        /**
         * The user has denied the permission!
         * You need to handle this in your code
         * *  */
        Toast.makeText(mActivity, "User has denied the permissions", Toast.LENGTH_LONG).show()
    }

    override fun onResponse(context: Context, ad: Ad) {
        mActivity!!.runOnUiThread {
            adapter = CustomListAdapter(mActivity!!, ad)
            recyclerView!!.adapter = adapter
        }
    }

    companion object {
        fun newInstance(pos: Int): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            args.putInt("pos", pos)
            fragment.arguments = args
            return fragment
        }
    }
}