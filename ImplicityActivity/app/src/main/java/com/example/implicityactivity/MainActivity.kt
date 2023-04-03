package com.example.implicityactivity

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.example.implicityactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWeb.setOnClickListener(this)
        binding.btnGoogle.setOnClickListener(this)
        binding.btnSearch.setOnClickListener(this)
        binding.btnText.setOnClickListener(this)
        binding.btnCamera.setOnClickListener(this)
        binding.btnImplicity.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        try {
            when (v?.id) {
                //웹뷰를 암시적 인텐트로 불러옴
                R.id.btnWeb -> {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.data = Uri.parse("http://www.naver.com")
                    startActivity(intent)
                }
                R.id.btnGoogle -> {
                    val latitude = 39.111111
                    val logitude = 127.111111
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.data = Uri.parse("http://google.com/maps?q=${latitude},${logitude}")
                    startActivity(intent)
                }
                R.id.btnSearch -> {
                    val intent = Intent()
                    intent.action = Intent.ACTION_WEB_SEARCH
                    intent.putExtra(SearchManager.QUERY, binding.edtSearch.text.toString())
                    startActivity(intent)
                }
                R.id.btnText -> {
                    val intent = Intent()
                    intent.action = Intent.ACTION_SENDTO
                    intent.putExtra("sms_body", "${binding.edtSearch.text.toString()}")
                    intent.data = Uri.parse("smsto:" + Uri.encode("010-1234-5678"))
                    startActivity(intent)
                }
                //카메라를 암시적 인텐트로 불러옴
                R.id.btnCamera -> {
                    val intent = Intent()
                    intent.action = MediaStore.ACTION_IMAGE_CAPTURE
                    startActivity(intent)
                }
                R.id.btnImplicity -> {
                    val intent = Intent()
                    intent.action = "ACTION_EDIT"
                    intent.putExtra("message", "${binding.edtSearch.text.toString()}")
                    startActivity(intent)
                }
            }
        } catch (e: java.lang.Exception) {
            Log.e("MainActivity", "${e.stackTrace.toString()}")
        }

    }
}
