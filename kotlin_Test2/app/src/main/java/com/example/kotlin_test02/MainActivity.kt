package com.example.kotlin_test02

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_test02.databinding.ActivityMainBinding
import com.example.kotlin_test02.databinding.RegisterLayout2Binding
import com.example.kotlin_test02.databinding.RegisterLayout3Binding
import com.example.kotlin_test02.databinding.RegisterLayoutBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCustomDialog.setOnClickListener(this)
        binding.btnCustomDialog2.setOnClickListener(this)
        binding.btnCustomDialog3.setOnClickListener(this)
    }// end of create

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnCustomDialog -> {
                val userBinding: RegisterLayoutBinding
                val dialogBuilder = AlertDialog.Builder(this)
                var userDialog: AlertDialog
                //사용자화면 인플렉션하기
                userBinding = RegisterLayoutBinding.inflate(layoutInflater)
                //사용자다이얼로그 제목, 뷰설정 보이기
                dialogBuilder.setTitle("id 입력창")
                dialogBuilder.setView(userBinding.root)
                //dialogBuilder.create() dialogBulder 정보를 dismiss()새로추가해서 userDialog 넘겨줌
                userDialog = dialogBuilder.create()
                userDialog.show()
                //이벤트 처리하기
                userBinding.btnCancel.setOnClickListener {
                    Toast.makeText(applicationContext, "취소되었습니다", Toast.LENGTH_SHORT).show()
                    userDialog.dismiss()
                }
                userBinding.btnRegister.setOnClickListener {
                    binding.tvMessage1.text = userBinding.edtid.text.toString()
                    userDialog.dismiss()
                }
            }
            R.id.btnCustomDialog2 -> {
                val userLayout2Binding: RegisterLayout2Binding
                val dialogBuilder = AlertDialog.Builder(this)
                var userDialog: AlertDialog
                //사용자화면 인플렉션하기
                userLayout2Binding = RegisterLayout2Binding.inflate(layoutInflater)
                //사용자다이얼로그 제목, 뷰설정 보이기
                dialogBuilder.setTitle("pw 입력창")
                dialogBuilder.setView(userLayout2Binding.root)
                //dialogBuilder.create() dialogBulder 정보를 dismiss()새로추가해서 userDialog 넘겨줌
                userDialog = dialogBuilder.create()
                userDialog.show()
                //이벤트 처리하기
                userLayout2Binding.btnCancel2.setOnClickListener {
                    Toast.makeText(applicationContext, "취소되었습니다", Toast.LENGTH_SHORT).show()
                    userDialog.dismiss()
                }
                userLayout2Binding.btnRegister2.setOnClickListener {
                    binding.tvMessage2.text = userLayout2Binding.edtpw.text.toString()
                    userDialog.dismiss()
                }
            }
            R.id.btnCustomDialog3 -> {
                val userLayout3Binding: RegisterLayout3Binding
                val dialogBuilder = AlertDialog.Builder(this)
                var userDialog: AlertDialog
                //사용자화면 인플렉션하기
                userLayout3Binding = RegisterLayout3Binding.inflate(layoutInflater)
                //사용자다이얼로그 제목, 뷰설정 보이기
                dialogBuilder.setTitle("name 입력창")
                dialogBuilder.setView(userLayout3Binding.root)
                //dialogBuilder.create() dialogBulder 정보를 dismiss()새로추가해서 userDialog 넘겨줌
                userDialog = dialogBuilder.create()
                userDialog.show()
                //이벤트 처리하기
                userLayout3Binding.btnCancel3.setOnClickListener {
                    Toast.makeText(applicationContext, "취소되었습니다", Toast.LENGTH_SHORT).show()
                    userDialog.dismiss()
                }
                userLayout3Binding.btnRegister3.setOnClickListener {
                    binding.tvMessage3.text = userLayout3Binding.edtname.text.toString()
                    userDialog.dismiss()
                }
            }
        }
    }

}
