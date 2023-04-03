package com.example.implicityactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.implicityactivity.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    lateinit var binding: ActivitySubBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(applicationContext, "${intent.getStringExtra("message")}", Toast.LENGTH_SHORT).show()
    }
}