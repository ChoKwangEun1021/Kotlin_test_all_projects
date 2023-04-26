package com.example.firebase23041909

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebase23041909.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra("user") as User

        binding.keyText.text = user.userKey
        binding.nameEdit.setText(user.userName)
        binding.ageEdit.setText(user.userAge)
        binding.noEdit.setText(user.userNo)
        
        binding.updateBtn.setOnClickListener { 
            val userDAO: UserDAO = UserDAO()
            val hashMap: HashMap<String, Any> = HashMap()
            hashMap["userAge"] = binding.ageEdit.text.trim().toString()
            hashMap["userName"] = binding.nameEdit.text.trim().toString()
            hashMap["userNo"] = binding.noEdit.text.trim().toString()
            userDAO.userUpdate(binding.keyText.text.toString(), hashMap).addOnSuccessListener{
                Toast.makeText(this, "수정 성공", Toast.LENGTH_SHORT).show()
                binding.stateText.text = "수정 성공"
            }.addOnFailureListener {
                Toast.makeText(this, "수정 실패", Toast.LENGTH_SHORT).show()
                binding.stateText.text = "수정 실패"
            }
        }
        
        binding.listBtn.setOnClickListener { 
            val intent = Intent(applicationContext, ListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}