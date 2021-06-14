package com.kt.launchmyapps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.appsdk.ApkExtractor
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var adapter: RecyclerView.Adapter<*>? = null
    var recyclerViewLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewLayoutManager = GridLayoutManager(this@MainActivity, 1)
        rc_apps!!.layoutManager = recyclerViewLayoutManager
        adapter = AppsAdapter(this@MainActivity,
                ApkExtractor(this@MainActivity).getAllInstalledApkInfo())
        rc_apps!!.adapter = adapter
    }
}