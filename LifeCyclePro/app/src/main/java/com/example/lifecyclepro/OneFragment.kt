package com.example.lifecyclepro

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifecyclepro.databinding.FragmentOneBinding

class OneFragment : Fragment() {
    lateinit var binding: FragmentOneBinding
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneBinding.inflate(inflater)
        binding.btnSend1.setOnClickListener {
            mainActivity.changeFragment("frag2", binding.edtMessage1.text.toString())
        }
        binding.tvMessage1.text = arguments?.getString("name")
        return binding.root
    }

}