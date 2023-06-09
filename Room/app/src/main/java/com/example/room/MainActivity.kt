package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.room.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var helper: RoomHelper
    lateinit var memoAdapter: RecyclerAdapter
    lateinit var memoDAO: RoomMemoDAO
    val memoList = mutableListOf<RoomMemo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helper = Room.databaseBuilder(this, RoomHelper::class.java, "room_db")
            .build()

        memoDAO = helper.roomMemoDao()
        memoAdapter = RecyclerAdapter(memoList)

        refreshAdapter()

        with(binding){
            recyclerMemo.adapter = memoAdapter
            recyclerMemo.layoutManager = LinearLayoutManager(this@MainActivity)

            buttonSave.setOnClickListener {
                val content = editMemo.text.toString()
                if (content.isNotEmpty()){
                    val datetime = System.currentTimeMillis()
                    val memo = RoomMemo(content, datetime)
                    editMemo.setText("")
                    insertMemo(memo)

                }
            }
        }
    }

    fun insertMemo(memo: RoomMemo){
        CoroutineScope(Dispatchers.IO).launch{
            memoDAO.insert(memo)
            refreshAdapter()
        }

    }

    fun refreshAdapter(){
        CoroutineScope(Dispatchers.IO).launch {
            memoList.clear()
            memoList.addAll(memoDAO.getAll())
            with(Dispatchers.Main){
//                memoAdapter.notifyDataSetChanged()
            }
        }
    }
}