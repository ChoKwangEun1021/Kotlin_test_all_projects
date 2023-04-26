package com.example.firebase23041909

import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase23041909.databinding.ActivityListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBinding
    lateinit var userAdapter: UserAdapter
    lateinit var userList: MutableList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userList = mutableListOf()
        userAdapter = UserAdapter(this, userList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.
        adapter = userAdapter

        getFireBaseUserList()

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //여기서 우리가 구현해야될 내용
                val position = viewHolder.bindingAdapterPosition
                val userDAO = UserDAO()
                when(direction){
                    ItemTouchHelper.LEFT ->{
                        val key = userList.get(position).userKey
                        userDAO.userDelete(key).addOnSuccessListener {
                            Toast.makeText(applicationContext, "삭제 성공", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(applicationContext, "삭제 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                }//end of when
            }//end of onSwiped

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.BLUE)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_24)
                    .addSwipeLeftLabel("delete")
                    .setSwipeLeftLabelColor(Color.WHITE)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }).attachToRecyclerView(binding.recyclerView)//end of ItemTouchHelper

    }

    private fun getFireBaseUserList() {
        val userDAO = UserDAO()
        userDAO.userSelect()?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                //snapshot = RecordSet
                for (dataSnapshot in snapshot.children){
                    val user = dataSnapshot.getValue(User::class.java)
                    user?.userKey = dataSnapshot.key.toString()
                    if (user != null){
                        userList.add(user)
                    }
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ListActivity", "파이어베이스 데이터 로딩 실패 ${error.toString()}")
                Toast.makeText(applicationContext, "파이어베이스 데이터 로딩  실패 ${error.toString()}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}