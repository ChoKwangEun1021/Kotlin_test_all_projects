package com.example.memberregistersqlitepro

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.memberregistersqlitepro.databinding.ItemRecyclerBinding

class RecyclerAdapter(val member: MutableList<Member>): RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount() = member.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val binding = holder.binding
        with(binding){
            tvNickName.text = member.get(position).name
            tvLevel.text = member.get(position).level
            tvEmail.text = member.get(position).email

            root.setOnClickListener {
                Toast.makeText(binding.root.context, "${member.get(position).toString()}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class CustomViewHolder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root)
}