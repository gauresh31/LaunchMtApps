package com.kt.launchmyapps

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kt.appsdk.ApkExtractor


class AppsAdapter(context: Context, list: List<String>) :
    RecyclerView.Adapter<AppsAdapter.ViewHolder>() {
    private var context: Context = context
    var stringList: List<String> = list

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardView: CardView = view.findViewById(R.id.card_view)
        var imageView: ImageView = view.findViewById(R.id.imageview) as ImageView
        var textView_App_Name: TextView = view.findViewById(R.id.apk_name)
        var textView_app_package_name: TextView = view.findViewById(R.id.apk_package_name)
        var textView_activity_name: TextView = view.findViewById(R.id.apk_activity_name)
        var textView_version_name: TextView = view.findViewById(R.id.apk_version_name)
        var textView_version_code: TextView = view.findViewById(R.id.apk_version_code)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view2: View =
            LayoutInflater.from(context).inflate(R.layout.cardview_layout, parent, false)
        return ViewHolder(view2)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val apkInfoExtractor = ApkExtractor(context)
        val applicationPackageName = stringList[position]
        val applicationLabelName: String? = apkInfoExtractor.getAppName(applicationPackageName)
        val drawable: Drawable? = apkInfoExtractor.getAppIconByPackageName(applicationPackageName)
        viewHolder.textView_App_Name.text = applicationLabelName

        val applPackage = "Package : $applicationPackageName"
        viewHolder.textView_app_package_name.text = applPackage

        val actName = apkInfoExtractor.getLauncherActivity(applicationPackageName)
        val activity_name = "Activity : $actName"
        viewHolder.textView_activity_name.text = activity_name

        val name = apkInfoExtractor.getVersionName()
        val versionName = "Version Name : $name"
        viewHolder.textView_version_name.text = versionName

        val code = apkInfoExtractor.getVersionCode()
        val versionCode = "Version Code : $code"
        viewHolder.textView_version_code.text = versionCode

        viewHolder.imageView.setImageDrawable(drawable)

        viewHolder.cardView.setOnClickListener {
            val intent: Intent? =
                context.packageManager.getLaunchIntentForPackage(applicationPackageName)
            if (intent != null) {
                context.startActivity(intent)
            } else {
                Toast.makeText(
                    context,
                    "$applicationPackageName Error, Please Try Again.", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return stringList.size
    }
}