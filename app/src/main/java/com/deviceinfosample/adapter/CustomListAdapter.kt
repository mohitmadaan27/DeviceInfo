package com.deviceinfosample.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.deviceinfosample.adapter.CustomListAdapter.CustomViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.deviceinfosample.R
import com.an.deviceinfo.ads.Ad
import com.an.deviceinfo.location.DeviceLocation
import com.an.deviceinfo.userapps.UserApps
import com.an.deviceinfo.usercontacts.UserContacts
import android.widget.TextView
import com.an.deviceinfo.device.model.*

class CustomListAdapter : RecyclerView.Adapter<CustomViewHolder> {
    private var context: Context
    private var deviceList: List<*>? = null
    private var `object`: Any? = null

    constructor(context: Context, deviceList: List<*>?) {
        this.context = context
        this.deviceList = deviceList
    }

    constructor(context: Context, `object`: Any?) {
        this.context = context
        this.`object` = `object`
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.fragment_list_item, null)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if (deviceList == null) {
            if (`object` is Ad) {
                handleAdInfo(holder, position)
            } else if (`object` is DeviceLocation) {
                handleLocationInfo(holder, position)
            } else if (`object` is App) {
                handleAppInfo(holder, position)
            } else if (`object` is Battery) {
                handleBatteryInfo(holder, position)
            } else if (`object` is Memory) {
                handleMemoryInfo(holder, position)
            } else if (`object` is Network) {
                handleNetworkInfo(holder, position)
            } else if (`object` is Device) {
                handleDeviceInfo(holder, position)
            }
            return
        }
        val `object` = deviceList!![position]!!
        if (`object` is UserApps) {
            holder.textView.text = `object`.appName
            holder.desc.text = `object`.packageName
        } else {
            holder.textView.text = (`object` as UserContacts).displayName
            holder.desc.text = `object`.mobileNumber
        }
    }

    override fun getItemCount(): Int {
        return if (deviceList == null) `object`!!.javaClass.declaredFields.size else deviceList!!.size
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView
        val desc: TextView

        init {
            textView = itemView.findViewById<View>(R.id.textView) as TextView
            desc = itemView.findViewById<View>(R.id.textDesc) as TextView
        }
    }

    private fun handleAdInfo(holder: CustomViewHolder, position: Int) {
        if (position == 0) {
            holder.textView.text = "AdvertisingId:"
            holder.desc.text = (`object` as Ad?)!!.advertisingId
        } else {
            holder.textView.text = "Allow to track ads:"
            holder.desc.text = (`object` as Ad?)!!.isAdDoNotTrack.toString()
        }
    }

    private fun handleLocationInfo(holder: CustomViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.textView.text = "Lattitude:"
                holder.desc.text = (`object` as DeviceLocation?)!!.latitude.toString()
            }
            1 -> {
                holder.textView.text = "Longitude:"
                holder.desc.text = (`object` as DeviceLocation?)!!.longitude.toString()
            }
            2 -> {
                holder.textView.text = "Address Line 1:"
                holder.desc.text = (`object` as DeviceLocation?)!!.addressLine1
            }
            3 -> {
                holder.textView.text = "City:"
                holder.desc.text = (`object` as DeviceLocation?)!!.city
            }
            4 -> {
                holder.textView.text = "State:"
                holder.desc.text = (`object` as DeviceLocation?)!!.state
            }
            5 -> {
                holder.textView.text = "CountryCode:"
                holder.desc.text = (`object` as DeviceLocation?)!!.countryCode
            }
            6 -> {
                holder.textView.text = "Postal Code:"
                holder.desc.text = (`object` as DeviceLocation?)!!.postalCode
            }
        }
    }

    private fun handleAppInfo(holder: CustomViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.textView.text = "App Name:"
                holder.desc.text = (`object` as App?)!!.appName
            }
            1 -> {
                holder.textView.text = "Package Name:"
                holder.desc.text = (`object` as App?)!!.packageName
            }
            2 -> {
                holder.textView.text = "Activity Name:"
                holder.desc.text = (`object` as App?)!!.activityName
            }
            3 -> {
                holder.textView.text = "App Version Name:"
                holder.desc.text = (`object` as App?)!!.appVersionName
            }
            4 -> {
                holder.textView.text = "App Version Code:"
                holder.desc.text = (`object` as App?)!!.appVersionCode.toString()
            }
        }
    }

    private fun handleBatteryInfo(holder: CustomViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.textView.text = "Battery Percent:"
                holder.desc.text = (`object` as Battery?)!!.batteryPercent.toString()
            }
            1 -> {
                holder.textView.text = "Is Phone Charging:"
                holder.desc.text = (`object` as Battery?)!!.isPhoneCharging.toString()
            }
            2 -> {
                holder.textView.text = "Battery Health:"
                holder.desc.text = (`object` as Battery?)!!.batteryHealth
            }
            3 -> {
                holder.textView.text = "Battery Technology:"
                holder.desc.text = (`object` as Battery?)!!.batteryTechnology
            }
            4 -> {
                holder.textView.text = "Battery Temperature:"
                holder.desc.text = (`object` as Battery?)!!.batteryTemperature.toString()
            }
            5 -> {
                holder.textView.text = "Battery Voltage:"
                holder.desc.text = (`object` as Battery?)!!.batteryVoltage.toString()
            }
            6 -> {
                holder.textView.text = "Charging Source:"
                holder.desc.text = (`object` as Battery?)!!.chargingSource
            }
            7 -> {
                holder.textView.text = "Is Battery Present:"
                holder.desc.text = (`object` as Battery?)!!.isBatteryPresent.toString()
            }
        }
    }

    private fun handleMemoryInfo(holder: CustomViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.textView.text = "Has external Momeny Card:"
                holder.desc.text =
                    (`object` as Memory?)!!.isHasExternalSDCard.toString()
            }
            1 -> {
                holder.textView.text = "Total RAM:"
                holder.desc.text =
                    convertToGb((`object` as Memory?)!!.totalRAM).toString() + " GB"
            }
            2 -> {
                holder.textView.text = "Total Internal Memory Space:"
                holder.desc.text =
                    convertToGb((`object` as Memory?)!!.totalInternalMemorySize).toString() + " GB"
            }
            3 -> {
                holder.textView.text = "Available Memory Space:"
                holder.desc.text =
                    convertToGb((`object` as Memory?)!!.availableInternalMemorySize).toString() + " GB"
            }
            4 -> {
                holder.textView.text = "Total External Memory Space:"
                holder.desc.text =
                    convertToGb((`object` as Memory?)!!.totalExternalMemorySize).toString() + " GB"
            }
            5 -> {
                holder.textView.text = "Available External Momory Space:"
                holder.desc.text =
                    convertToGb((`object` as Memory?)!!.availableExternalMemorySize).toString() + " GB"
            }
        }
    }

    private fun handleNetworkInfo(holder: CustomViewHolder, position: Int) {
        when (position) {
            0 -> holder.textView.text = "IMEI:"
            1 -> holder.textView.text = "IMSI:"
            2 -> {
                holder.textView.text = "Phone Type:"
                holder.desc.text = (`object` as Network?)!!.phoneType
            }
            3 -> holder.textView.text = "Phone Number:"
            4 -> {
                holder.textView.text = "Carrier:"
                holder.desc.text = (`object` as Network?)!!.operator
            }
            5 -> {
                holder.textView.text = "SIM Serial:"
                holder.desc.text = (`object` as Network?)!!.getsIMSerial()
            }
            6 -> {
                holder.textView.text = "is SIM Locked:"
                holder.desc.text = (`object` as Network?)!!.isSimNetworkLocked.toString()
            }
            7 -> {
                holder.textView.text = "is Nfc Enabled:"
                holder.desc.text = (`object` as Network?)!!.isNfcEnabled.toString()
            }
            8 -> {
                holder.textView.text = "is Nfc Present:"
                holder.desc.text = (`object` as Network?)!!.isNfcPresent.toString()
            }
            9 -> {
                holder.textView.text = "is Wifi Enabled:"
                holder.desc.text = (`object` as Network?)!!.isWifiEnabled.toString()
            }
            10 -> {
                holder.textView.text = "is Network Available:"
                holder.desc.text =
                    (`object` as Network?)!!.isNetworkAvailable.toString()
            }
            11 -> {
                holder.textView.text = "Network Class:"
                holder.desc.text = (`object` as Network?)!!.networkClass
            }
            12 -> {
                holder.textView.text = "Network Type:"
                holder.desc.text = (`object` as Network?)!!.networkType
            }
        }
    }

    private fun handleDeviceInfo(holder: CustomViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.textView.text = "Manufacturer:"
                holder.desc.text = (`object` as Device?)!!.manufacturer
            }
            1 -> {
                holder.textView.text = "Model:"
                holder.desc.text = (`object` as Device?)!!.model
            }
            2 -> {
                holder.textView.text = "Build VersionCode Name:"
                holder.desc.text = (`object` as Device?)!!.buildVersionCodeName
            }
            3 -> {
                holder.textView.text = "Release Build Version:"
                holder.desc.text = (`object` as Device?)!!.releaseBuildVersion
            }
            4 -> {
                holder.textView.text = "Product:"
                holder.desc.text = (`object` as Device?)!!.product
            }
            5 -> {
                holder.textView.text = "Fingerprint:"
                holder.desc.text = (`object` as Device?)!!.fingerprint
            }
            6 -> {
                holder.textView.text = "Hardware:"
                holder.desc.text = (`object` as Device?)!!.hardware
            }
            7 -> {
                holder.textView.text = "Radio Version:"
                holder.desc.text = (`object` as Device?)!!.radioVersion
            }
            8 -> {
                holder.textView.text = "Device:"
                holder.desc.text = (`object` as Device?)!!.device
            }
            9 -> {
                holder.textView.text = "Board:"
                holder.desc.text = (`object` as Device?)!!.board
            }
            10 -> {
                holder.textView.text = "Display Version:"
                holder.desc.text = (`object` as Device?)!!.displayVersion
            }
            11 -> {
                holder.textView.text = "Build Brand:"
                holder.desc.text = (`object` as Device?)!!.buildBrand
            }
            12 -> {
                holder.textView.text = "Build Host:"
                holder.desc.text = (`object` as Device?)!!.buildHost
            }
            13 -> {
                holder.textView.text = "Build Time:"
                holder.desc.text = (`object` as Device?)!!.buildTime.toString()
            }
            14 -> {
                holder.textView.text = "Build User:"
                holder.desc.text = (`object` as Device?)!!.buildUser
            }
            15 -> {
                holder.textView.text = "Serial:"
                holder.desc.text = (`object` as Device?)!!.serial
            }
            16 -> {
                holder.textView.text = "OS Version:"
                holder.desc.text = (`object` as Device?)!!.osVersion
            }
            17 -> {
                holder.textView.text = "Language:"
                holder.desc.text = (`object` as Device?)!!.language
            }
            18 -> {
                holder.textView.text = "SDK Version:"
                holder.desc.text = (`object` as Device?)!!.sdkVersion.toString()
            }
            19 -> {
                holder.textView.text = "Screen Density:"
                holder.desc.text = (`object` as Device?)!!.screenDensity
            }
            20 -> {
                holder.textView.text = "Screen Height:"
                holder.desc.text = (`object` as Device?)!!.screenHeight.toString()
            }
            21 -> {
                holder.textView.text = "Screen Width:"
                holder.desc.text = (`object` as Device?)!!.screenWidth.toString()
            }
        }
    }

    private fun convertToGb(valInBytes: Long): Float {
        return java.lang.Float.valueOf(
            String.format(
                "%.2f",
                valInBytes.toFloat() / (1024 * 1024 * 1024)
            )
        )
    }
}