package com.example.doublerecyclerviewproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doublerecyclerviewproject.databinding.ItemListBinding

class ListAdapter(val data: MutableMap<String, MutableList<String>>) : RecyclerView.Adapter<ListAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding: ItemListBinding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val binding = holder.binding
        binding.tvName.text = data.keys.elementAt(position)
        //recyclerView2 : adapter, LinearLayoutManager 제공해야됨. adapter(데이터)
        val dataSubList: MutableList<DataSub> = mutableListOf<DataSub>()
        for (i in 1..10){
            if (i % 2 == 0){
                dataSubList.add(DataSub("${data.values.elementAt(position)[(i-1)%4]}", R.drawable.face_man))
            }else {
                dataSubList.add(DataSub("${data.values.elementAt(position)[(i-1)%4]}", R.drawable.face_woman))
            }
        }
        binding.recyclerview2.apply {
            adapter = ListAdapter2(dataSubList)
            layoutManager = LinearLayoutManager(binding.recyclerview2.context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    class CustomViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)
}