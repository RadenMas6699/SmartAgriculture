/*
 * Created by RadenMas on 16/8/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart_agriculture.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.radenmas.smart_agriculture.R
import com.radenmas.smart_agriculture.databinding.ActivityMainBinding
import com.radenmas.smart_agriculture.ui.info.InfoActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        b = ActivityMainBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        initView()
        onClick()
    }

    private fun onClick() {
        b.imgInformation.setOnClickListener {
            startActivity(Intent(this, InfoActivity::class.java))
        }

        b.switchPump.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                b.rlWaterPump.setBackgroundColor(resources.getColor(R.color.primary_light))
                FirebaseDatabase.getInstance().reference.child("control").child("pump").setValue(1)
            } else {
                b.rlWaterPump.setBackgroundColor(resources.getColor(R.color.white))
                FirebaseDatabase.getInstance().reference.child("control").child("pump").setValue(0)
            }
        }

        b.switchNutrition.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                b.rlNutritionPump.setBackgroundColor(resources.getColor(R.color.primary_light))
                FirebaseDatabase.getInstance().reference.child("control").child("nutrisi")
                    .setValue(1)
            } else {
                b.rlNutritionPump.setBackgroundColor(resources.getColor(R.color.white))
                FirebaseDatabase.getInstance().reference.child("control").child("nutrisi")
                    .setValue(0)
            }
        }
    }

    private fun initView() {
        val dt = Date()
        when (dt.hours) {
            in 0..5 -> {
                b.tvGreeting.text = resources.getString(R.string.greeting, "Malam")
                b.imgGreeting.setImageResource(R.drawable.ic_night)
            }
            in 6..10 -> {
                b.tvGreeting.text = resources.getString(R.string.greeting, "Pagi")
                b.imgGreeting.setImageResource(R.drawable.ic_sunrise)
            }
            in 11..14 -> {
                b.tvGreeting.text = resources.getString(R.string.greeting, "Siang")
                b.imgGreeting.setImageResource(R.drawable.ic_sun)
            }
            in 15..18 -> {
                b.tvGreeting.text = resources.getString(R.string.greeting, "Sore")
                b.imgGreeting.setImageResource(R.drawable.ic_sunset)
            }
            in 19..23 -> {
                b.tvGreeting.text = resources.getString(R.string.greeting, "Malam")
                b.imgGreeting.setImageResource(R.drawable.ic_night)
            }
        }

        getStatusPompa()
        getDataRealtime()
    }

    private fun getStatusPompa() {
        FirebaseDatabase.getInstance().getReference("control")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val pump = snapshot.child("pump").value.toString().toInt()
                    val nutrisi = snapshot.child("nutrisi").value.toString().toInt()
                    if (pump == 1) {
                        b.switchPump.isChecked = true
                        b.rlWaterPump.setBackgroundColor(resources.getColor(R.color.primary_light))
                    } else {
                        b.switchPump.isChecked = false
                        b.rlWaterPump.setBackgroundColor(resources.getColor(R.color.white))
                    }
                    if (nutrisi == 1) {
                        b.switchNutrition.isChecked = true
                        b.rlNutritionPump.setBackgroundColor(resources.getColor(R.color.primary_light))
                    } else {
                        b.switchNutrition.isChecked = false
                        b.rlNutritionPump.setBackgroundColor(resources.getColor(R.color.white))
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun getDataRealtime() {
        FirebaseDatabase.getInstance().reference.child("realtime")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val time = snapshot.child("time").value.toString().toLong()
                    val temp = snapshot.child("temp").value.toString().toFloat()
                    val soil1 = snapshot.child("soil1").value.toString()
                    val soil2 = snapshot.child("soil2").value.toString()
                    val ph = snapshot.child("ph").value.toString().toFloat()
                    val tank = snapshot.child("water_level").value.toString()

                    val date = Date(time * 1000)
                    val formatDate = SimpleDateFormat("HH:mm zz\nEEEE, dd MMM yyyy")

                    b.tvLastUpdate.text = formatDate.format(date)

                    b.tvWaterTemp.text = "%.0f".format(temp) + "℃"
                    b.tvSoilMoisture1.text = "$soil1"
                    b.tvSoilMoisture2.text = "$soil2"
                    b.tvWaterPh.text = "%.2f".format(ph)
                    b.tvWaterLevel.text = "${tank}%"
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}