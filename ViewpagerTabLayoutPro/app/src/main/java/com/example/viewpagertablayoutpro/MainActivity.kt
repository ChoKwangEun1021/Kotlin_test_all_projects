package com.example.viewpagertablayoutpro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpagertablayoutpro.databinding.ActivityMainBinding
import com.example.viewpagertablayoutpro.databinding.UsertabButtonBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var customAdapter: CustomAdapter
    lateinit var tabTitleList: MutableList<String>
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customAdapter = CustomAdapter(this)
        customAdapter.addListFragment(OneFragment())
        customAdapter.addListFragment(TwoFragment())
        customAdapter.addListFragment(ThreeFragment())
        tabTitleList = mutableListOf("car", "home", "air")

        binding.viewpager2.adapter = customAdapter
        binding.viewpager2.orientation = ViewPager2.ORIENTATION_VERTICAL

//        val tab1: TabLayout.Tab = binding.tabLayout.newTab()
//        val tab2: TabLayout.Tab = binding.tabLayout.newTab()
//        val tab3: TabLayout.Tab = binding.tabLayout.newTab()
//        tab1.text = "frag1"
//        tab2.text = "frag2"
//        tab3.text = "frag3"
//        binding.tabLayout.addTab(tab1)
//        binding.tabLayout.addTab(tab2)
//        binding.tabLayout.addTab(tab3)
        TabLayoutMediator(binding.tabLayout, binding.viewpager2){tab,position ->
            tab.setCustomView(tabCustomView(position))
        }.attach()

        //1. 액션바대신에 툴바로대체
        setSupportActionBar(binding.toolbar)
        //2. ActionBarDrawerToggle 버튼 적용
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.drawer_open, R.string.drawer_close)
        //3. 업버튼 활성화
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //4. 토글 sync
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.item_info -> Toast.makeText(applicationContext, "info", Toast.LENGTH_SHORT).show()
                }
                return false
            }

        })
    }

    fun tabCustomView(position: Int): View {
        val binding = UsertabButtonBinding.inflate(layoutInflater)
        when(position){
            0 -> binding.ivIcon.setImageResource(R.drawable.electric_car)
            1 -> binding.ivIcon.setImageResource(R.drawable.house)
            2 -> binding.ivIcon.setImageResource(R.drawable.electric_car)
        }
        binding.tvTabName.text = tabTitleList.get(position)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}