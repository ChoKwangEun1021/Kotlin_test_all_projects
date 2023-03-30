package com.example.appbarpro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appbarpro.databinding.ActivityMainBinding
import com.example.appbarpro.databinding.UsertabButtonBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var customViewpagerAdaapter: CustomViewpagerAdaapter
    lateinit var tabTitleList: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customViewpagerAdaapter = CustomViewpagerAdaapter(this)
        customViewpagerAdaapter.addListFragment(OneFragment())
        customViewpagerAdaapter.addListFragment(TwoFragment())
        customViewpagerAdaapter.addListFragment(ThreeFragment())
        tabTitleList = mutableListOf("car", "home", "air")
        binding.viewpager2.adapter = customViewpagerAdaapter

        //탭레이아웃 뷰페이저 연동
        TabLayoutMediator(binding.tablayout, binding.viewpager2){tab,position ->
            tab.setCustomView(tabCustomView(position))
        }.attach()

        binding.eftb.setOnClickListener {
            Toast.makeText(applicationContext, "확장탭이다", Toast.LENGTH_SHORT).show()
        }
    }//end of onCreate

    fun tabCustomView(position: Int): View {
        val binding = UsertabButtonBinding.inflate(layoutInflater)
        when(position){
            0 -> binding.ivIcon.setImageResource(R.drawable.electric_car)
            1 -> binding.ivIcon.setImageResource(R.drawable.house)
            2 -> binding.ivIcon.setImageResource(R.drawable.airplane)
        }
        binding.tvTabName.text = tabTitleList.get(position)
        return binding.root
    }

}//end of class