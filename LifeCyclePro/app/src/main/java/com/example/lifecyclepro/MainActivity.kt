package com.example.lifecyclepro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lifecyclepro.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var oneFragment: OneFragment
    lateinit var twoFragment: TwoFragment
    lateinit var threeFragment: ThreeFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        oneFragment = OneFragment()
        twoFragment = TwoFragment()
        threeFragment = ThreeFragment()

        //1. 탭레이아웃에 탭메뉴추가
        val tab1: TabLayout.Tab = binding.tabLayout.newTab()
        val tab2: TabLayout.Tab = binding.tabLayout.newTab()
        val tab3: TabLayout.Tab = binding.tabLayout.newTab()
        tab1.text = "frag1"
        tab2.text = "frag2"
        tab3.text = "frag3"
        binding.tabLayout.addTab(tab1)
        binding.tabLayout.addTab(tab2)
        binding.tabLayout.addTab(tab3)

        //2. 탭레이아웃 이벤트 처리
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.ivCar.visibility = View.GONE
                when(tab?.text){
                    "frag1" -> changeFragment("frag1", null)
                    "frag2" -> changeFragment("frag2", null)
                    "frag3" -> changeFragment("frag3", null)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun changeFragment(tabName: String, data: String?){
        val transaction = supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("name", data)
        when(tabName){
            "frag1" -> {
                val tab = binding.tabLayout.getTabAt(0)
                binding.tabLayout.selectTab(tab)
                oneFragment.arguments = bundle
                transaction.replace(R.id.frameLayout, oneFragment)
            }
            "frag2" -> {
                val tab = binding.tabLayout.getTabAt(1)
                binding.tabLayout.selectTab(tab)
                twoFragment.arguments = bundle
                transaction.replace(R.id.frameLayout, twoFragment)
            }
            "frag3" -> {
                val tab = binding.tabLayout.getTabAt(2)
                binding.tabLayout.selectTab(tab)
                threeFragment.arguments = bundle
                transaction.replace(R.id.frameLayout, threeFragment)
            }
        }
        transaction.commit()
    }
}