package com.example.bindservicemessengerpro

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.widget.Toast
import com.example.bindservicemessengerpro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var serviceConnection: ServiceConnection
    var messenger: Messenger? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //1. 바인딩 서비스를 진행하기 위해서 ServiceConnection 설계하기
        serviceConnection = object : ServiceConnection{
            //BindService 요청했을때 연결이되면 콜백이 진행이되고 서비스에서 제공한 IBinder를 매개변수로 가져온다
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                messenger = Messenger(service)
                Toast.makeText(applicationContext, "Messenger Service 객체받음", Toast.LENGTH_SHORT).show()
                binding.btnClick1.isEnabled = false
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Toast.makeText(applicationContext, "Messenger Service 종료", Toast.LENGTH_SHORT).show()
                binding.btnClick1.isEnabled = true
            }
        }

        //바인드 서비스를 요청함
        binding.btnClick1.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        //IBinder 매개변수를 통해서 전달해온 메신저를 통해서 데이터, 명령을 서비스에 전달한다
        binding.btnClick2.setOnClickListener {
            if (messenger != null){
                val message = Message()
                message.what = 10
                message.obj = "안녕"
                messenger?.send(message)
            }
        }
    }
}