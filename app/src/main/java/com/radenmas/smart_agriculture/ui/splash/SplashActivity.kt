/*
 * Created by RadenMas on 16/8/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart_agriculture.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.radenmas.smart_agriculture.R
import com.radenmas.smart_agriculture.databinding.ActivitySplashBinding
import com.radenmas.smart_agriculture.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    lateinit var b: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        initView()
        onClick()
    }

    private fun onClick() {

    }

    private fun initView() {
        val versionName = packageManager.getPackageInfo(packageName, 0).versionName
        b.tvAppVersion.text = resources.getString(R.string.app_version, versionName)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500)
    }
}