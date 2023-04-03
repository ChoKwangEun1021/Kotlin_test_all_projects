package com.example.onsaveinstancestatepro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onsaveinstancestatepro.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}