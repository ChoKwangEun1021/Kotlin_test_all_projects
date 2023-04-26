package com.example.firebase23041909

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase23041909.databinding.UserLayoutBinding

class UserAdapter(val context: Context, val userList: MutableList<User>): RecyclerView.Adapter<UserAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = UserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val binding = holder.binding
        val user = userList.get(position)
        binding.tvKey.text = user.userKey
        binding.btnAge.text = user.userAge
        binding.btnName.text = user.userName
        binding.btnNo.text = user.userNo

        binding.root.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("user", user)
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }

    inner class CustomViewHolder(val binding: UserLayoutBinding): RecyclerView.ViewHolder(binding.root)
}