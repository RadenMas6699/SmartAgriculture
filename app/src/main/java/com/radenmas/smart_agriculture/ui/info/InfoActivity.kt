/*
 * Created by RadenMas on 16/8/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart_agriculture.ui.info

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.radenmas.smart_agriculture.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    lateinit var b: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        b = ActivityInfoBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        initView()
        onClick()
    }

    private fun onClick() {
        b.imgBack.setOnClickListener { onBackPressed() }
    }

    private fun initView() {

    }
}