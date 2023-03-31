package com.example.intentpro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.intentpro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCallActivity2.setOnClickListener {
            //명시적 인텐트
            val intent: Intent= Intent(this, MainActivity2::class.java)
            intent.putExtra("name", "홍길동")
            intent.putExtra("age", 27)
            startActivity(intent)
        }

        binding.btnCallActivity3.setOnClickListener {
            //명시적 인텐트
            val intent: Intent= Intent(this, MainActivity3::class.java)
            intent.putExtra("tvName", "고길동")
            intent.putExtra("age2", 99)
            startActivity(intent)
        }

        val activityResultLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                //콜백 함수
                if (it.data?.getIntExtra("requestCode", 0) == 20) {
                    Toast.makeText(applicationContext, "${it.data?.getIntExtra("sum", 0)}", Toast.LENGTH_SHORT).show()
                }else if (it.data?.getIntExtra("requestCode", 0) == 21) {
                    Toast.makeText(applicationContext, "${it.data?.getIntExtra("sum", 0)}", Toast.LENGTH_SHORT).show()
                }else if (it.data?.getIntExtra("requestCode", 0) == 60) {
                    Toast.makeText(applicationContext, "== ${it.data?.getIntExtra("sum", 0)}", Toast.LENGTH_SHORT).show()
                }else if (it.data?.getIntExtra("requestCode", 0) == 70) {
                    Toast.makeText(applicationContext, "== ${it.data?.getIntExtra("sum", 0)}", Toast.LENGTH_SHORT).show()
                }
            }

        binding.btnCallActivity4.setOnClickListener {
            //명시적 인텐트
            val intent: Intent= Intent(this, MainActivity4::class.java)
            intent.putExtra("x", 45)
            intent.putExtra("y", 23)
            intent.putExtra("operator", "+")
            intent.putExtra("requestCode", 20)
            activityResultLauncher.launch(intent)
//            startActivityForResult(intent, 20)
        }

        binding.btnCallActivity5.setOnClickListener {
            //명시적 인텐트
            val intent: Intent= Intent(this, MainActivity5::class.java)
            intent.putExtra("x", 45)
            intent.putExtra("y", 23)
            intent.putExtra("operator", "-")
            intent.putExtra("requestCode", 21)
            activityResultLauncher.launch(intent)
//            startActivityForResult(intent, 21)
        }

        binding.btnCallActivity6.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity6::class.java)
            intent.putExtra("x", 45)
            intent.putExtra("y", 23)
            intent.putExtra("operator", "+")
            intent.putExtra("requestCode", 60)
            activityResultLauncher.launch(intent)
        }

        binding.btnCallActivity7.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity7::class.java)
            intent.putExtra("x", 45)
            intent.putExtra("y", 23)
            intent.putExtra("operator", "*")
            intent.putExtra("requestCode", 70)
            activityResultLauncher.launch(intent)
        }
    }// end of onCreate

//    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
//        super.onActivityResult(requestCode, resultCode, intent)
//        if (requestCode == 20 && resultCode == RESULT_OK) {
//            Toast.makeText(applicationContext, "${intent?.getIntExtra("sum", 0)}", Toast.LENGTH_SHORT).show()
//        }else if (requestCode == 21 && resultCode == RESULT_OK) {
//            Toast.makeText(applicationContext, "${intent?.getIntExtra("sum", 0)}", Toast.LENGTH_SHORT).show()
//        }
//    }
}//end of class