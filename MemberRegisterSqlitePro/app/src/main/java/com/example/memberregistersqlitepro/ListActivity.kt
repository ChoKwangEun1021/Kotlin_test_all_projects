package com.example.memberregistersqlitepro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memberregistersqlitepro.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBinding
    var memberList: MutableList<Member>? = mutableListOf<Member>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var dbHelper: DBHelper = DBHelper(applicationContext, MainActivity.DB_NAME, MainActivity.VERSION)
        memberList = dbHelper.selectAll()
        val recyclerAdapter = RecyclerAdapter(memberList!!)

        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnMain.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}