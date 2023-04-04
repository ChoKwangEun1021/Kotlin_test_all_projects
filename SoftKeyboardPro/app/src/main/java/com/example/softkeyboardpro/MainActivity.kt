package com.example.softkeyboardpro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.softkeyboardpro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    lateinit var inputMethodManager: InputMethodManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.btnSoftKeyShow.setOnClickListener(this)
        binding.btnSoftKeyHide.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSoftKeyShow ->{
                binding.edtData.requestFocus()
                inputMethodManager.showSoftInput(binding.edtData, InputMethodManager.SHOW_IMPLICIT)
            }
            R.id.btnSoftKeyHide ->{
                inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                binding.edtData.clearFocus()
            }
        }
    }
}