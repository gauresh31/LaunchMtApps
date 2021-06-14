package com.kt.appsdk

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import java.util.*


class ApkExtractor(var context: Context) {

    fun getAllInstalledApkInfo(): List<String> {
        val apkPackageName: MutableList<String> = ArrayList()
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        val resolveInfoList = context.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resolveInfoList) {
            val activityInfo = resolveInfo.activityInfo
            if (!this.isSystemPackage(resolveInfo)) {
                apkPackageName.add(activityInfo.applicationInfo.packageName)
            }
        }
        apkPackageName.sort()
        return apkPackageName
    }

    private fun isSystemPackage(resolveInfo: ResolveInfo): Boolean {
        return resolveInfo.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    fun getAppIconByPackageName(ApkTempPackageName: String?): Drawable? {
        return try {
            context.packageManager.getApplicationIcon(ApkTempPackageName!!)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ContextCompat.getDrawable(context, android.R.drawable.ic_media_play)
        }
    }

    fun getAppName(apkPackageName: String?): String? {
        var name = ""
        val applicationInfo: ApplicationInfo
        val packageManager = context.packageManager
        try {
            applicationInfo = packageManager.getApplicationInfo(apkPackageName!!, 0)
            name = packageManager.getApplicationLabel(applicationInfo) as String
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return name
    }

    fun getLauncherActivity(apkPackageName: String?): String? {
        var name = ""
        val applicationInfo: ApplicationInfo
        val packageManager = context.packageManager
        try {
            applicationInfo = packageManager.getApplicationInfo(apkPackageName!!, 0)
            name = packageManager.getLaunchIntentForPackage(
                applicationInfo.packageName)?.component?.shortClassName.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return name
    }

    fun getVersionName(): String? {
        var name = ""
        val packageManager = context.packageManager
        try {
            val info: PackageInfo = packageManager.getPackageInfo(context.packageName, 0)
            name = info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return name
    }

    fun getVersionCode(): String? {
        var code = ""
        val packageManager = context.packageManager
        try {
            val info: PackageInfo = packageManager.getPackageInfo(context.packageName, 0)
            code = info.versionCode.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return code
    }
}