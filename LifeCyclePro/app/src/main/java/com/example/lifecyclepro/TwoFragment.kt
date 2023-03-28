package com.example.lifecyclepro

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifecyclepro.databinding.FragmentTwoBinding

class TwoFragment : Fragment() {
    lateinit var binding: FragmentTwoBinding
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTwoBinding.inflate(inflater)
        binding.btnSend2.setOnClickListener {
            mainActivity.changeFragment("frag1", binding.edtMessage2.text.toString())
        }
        binding.tvMessage2.text = arguments?.getString("name")
        return binding.root
    }

}