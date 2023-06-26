package com.example.climberapp.ui.model

data class Dashboard(
    val alarms: List<AlarmX>,
    val bothclampclosed: Int,
    val bothclampopen: Int,
    val devices: List<Device>,
    val free: Int,
    val nodata: Int,
    val sites: List<SiteXX>,
    val sos: Int,
    val status: Int,
    val totalDevices: Int,
    val totalOffline: Int,
    val totalOffsite: Int,
    val totalOnline: Int,
    val totalOnsite: Int
)