package com.example.kotlin_test01

import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.kotlin_test01.databinding.ActivityMainBinding
import com.example.kotlin_test01.databinding.RegisterLayoutBinding
import com.example.kotlin_test01.databinding.ToastLayoutBinding

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToast.setOnClickListener(this)
        binding.btnDate.setOnClickListener(this)
        binding.btnTime.setOnClickListener(this)
        binding.btnDialog.setOnClickListener(this)
        binding.btnItemDialog.setOnClickListener(this)
        binding.btnMulitItemDialog.setOnClickListener(this)
        binding.btnSingleItemDialog.setOnClickListener(this)
        binding.btnCustomDialog.setOnClickListener(this)
        binding.btnFineLocate.setOnClickListener(this)
        binding.btnRington.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnToast -> {
//                val toast = Toast.makeText(applicationContext, "토스트 메세지 보이기", Toast.LENGTH_SHORT)
//                toast.duration = Toast.LENGTH_LONG
//                toast.setGravity(Gravity.TOP, 0, 200)
//                val toastLayout =
//                    LayoutInflater.from(applicationContext).inflate(R.layout.toast_layout, null)
//                toast.view = toastLayout.rootView
//                var toastLayoutBinding: ToastLayoutBinding
//                toastLayoutBinding = ToastLayoutBinding.inflate(layoutInflater)
//                toast.view = toastLayoutBinding.root
//                toastLayoutBinding.textView.setOnClickListener {
//                    Log.e("MainActivity", "++++++++++++++++")
//                }
//                toast.show()
            }
            R.id.btnDate -> {
                DatePickerDialog(this, this, 2023, 3-1, 23).show()
            }
            R.id.btnTime -> {
                TimePickerDialog(this, this, 13, 45, true).show()
            }
            R.id.btnDialog -> {
                val eventHandler = object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Toast.makeText(this@MainActivity, "${if(which == -1){"YES"}else{"NO"}}", Toast.LENGTH_SHORT).show()
                    }
                }
                AlertDialog.Builder(this).run {
                    setTitle("알림창")
                    setIcon(R.drawable.computer_24)
                    setMessage("알림창 정보를 보여드립니다.")
                    setPositiveButton("YES", eventHandler)
                    setNegativeButton("NO", eventHandler)
                    show()
                }
            }
            R.id.btnItemDialog -> {
                val eventHandler = object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Toast.makeText(this@MainActivity, "${if(which == -1){"YES"}else{"NO"}}", Toast.LENGTH_SHORT).show()
                    }
                }
                val items = arrayOf<String>("홍길동", "저길동", "구길동", "수길동")
                AlertDialog.Builder(this).run {
                    setTitle("알림창")
                    setIcon(R.drawable.computer_24)
                    setItems(items, object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            binding.btnItemDialog.text = items[which]
                        }
                    })
                    setNegativeButton("NO", eventHandler)
                    show()
                }
            }
            R.id.btnMulitItemDialog -> {
                val items = arrayOf<String>("홍길동", "저길동", "구길동", "수길동")
                AlertDialog.Builder(this).run {
                    setTitle("알림창")
                    setIcon(R.drawable.computer_24)
                    setMultiChoiceItems(items, booleanArrayOf(true, false, false, false), object : DialogInterface.OnMultiChoiceClickListener{
                        override fun onClick(
                            dialog: DialogInterface?,
                            which: Int,
                            isChecked: Boolean
                        ) {
                            if (isChecked == true){
                                binding.btnMulitItemDialog.text = items[which]
                            }
                        }

                    })
                    setPositiveButton("YES", object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            Toast.makeText(applicationContext, "선택했습니다", Toast.LENGTH_SHORT).show()
                        }

                    })
                    setNegativeButton("Cancel", null)
                    show()
                }
            }
            R.id.btnSingleItemDialog -> {
                val items = arrayOf<String>("홍길동", "저길동", "구길동", "수길동")
                AlertDialog.Builder(this).run {
                    setTitle("알림창")
                    setIcon(R.drawable.computer_24)
                    setSingleChoiceItems(items, 0, object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            binding.btnSingleItemDialog.text = items[which]
                        }
                    })
                    setNegativeButton("CLOSER", null)
                    setCancelable(false)
                    show()
                }.setCancelable(true)
            }
            R.id.btnCustomDialog -> {
                val userBinding: RegisterLayoutBinding
                val dialogBuilder = AlertDialog.Builder(this)
                var userDialog: AlertDialog
                //사용자화면 인플렉션하기
                userBinding = RegisterLayoutBinding.inflate(layoutInflater)
                //사용자다이얼로그 제목, 뷰설정 보이기
                dialogBuilder.setTitle("사용자 이름 입력하기 창")
                dialogBuilder.setIcon(R.drawable.computer_24)
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
                    binding.tvMessage.text = userBinding.edtName.text.toString()
                    userDialog.dismiss()
                }

            }
            R.id.btnFineLocate -> {
              val state = ContextCompat.checkSelfPermission(applicationContext, "android.permission.ACCESS_FINE_LOCATION")
                if(state == PackageManager.PERMISSION_GRANTED){
                    binding.tvMessage.text = "위치추적권한허용"
                }else{
                    binding.tvMessage.text = "위치추적권한거부"
                }
            }
            R.id.btnRington -> {
                //Uniform Resource Identifiar, 리소스를 구분하는 식별자.
                //안드로이드에서 URI의 역할은 리소스(외부 앱, 이미지, 텍스트 등)에 접근할 수 있는 식별자 역할
                val notificationUri : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val rington = RingtoneManager.getRingtone(applicationContext,notificationUri)
                rington.play()
            }
        }

    }//end of onClick

    //콜백함수
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        Toast.makeText(applicationContext, "${year} ${month+1} ${day}", Toast.LENGTH_SHORT).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Toast.makeText(applicationContext, "${hourOfDay}시 ${minute}분", Toast.LENGTH_SHORT).show()
    }
}