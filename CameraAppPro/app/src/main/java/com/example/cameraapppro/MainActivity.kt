package com.example.cameraapppro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cameraapppro.databinding.ActivityMainBinding
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var filePath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //1. 인텐트를 통해서 갤러리 앱에서 클릭을 하면 클릭된 이미지 Uri를 가져와서, 컨텐트리졸버를 이용해서 inputstream, BitMapFactory를 통해서
        //이미지 뷰 가져오기
        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {

            if(it.resultCode == RESULT_OK){
                //1-1 이미지를 가져오면 oom 발생할 수 있으므로, 화면에 출력될 원하는 사이즈로 비율설정을 해야한다
                val inSampleSize = calculateInSampleSize(Uri.fromFile(File(filePath)), resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize))
                //1-2 비트맵 옵션설정 비율설정
                val option = BitmapFactory.Options()
                option.inSampleSize = inSampleSize

                try {
                    //1-4 inputStream으로 BitmapFactory를 이용해서 이미지를 가져오기(oom 방지하기 위해서, option 사이즈비율 저장함)
                    var bitmap = BitmapFactory.decodeFile(filePath, option)
                    //1-5 이미지뷰에 비트맵을 저장
                    bitmap?.let {
                        binding.ivPicture.setImageBitmap(bitmap)
                    }?:let {
                        Log.e("MainActivity", "bitmapFactory를 통해서 가져온 비트맵 null 발생")
                    }
                }catch (e: Exception){
                    Log.e("MainActivity", "${e.printStackTrace()}")
                }
            }
        }
        //2. 갤러리앱에 암시적 인텐트방법으로 요청
        binding.btnCallImage.setOnClickListener {
            //2-1 카메라앱이 저장될 파일명을 만듬
            val timeStamp = SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Date())
            //2-2 카메라앱이 저장될 앱폴더 위치를 가져옴
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            //2-3 실제파일명을 만들어서 앱폴더 위치에 저장한다
            val file = File.createTempFile("__jpg_${timeStamp}_", ".jpg", storageDir)
            filePath = file.absolutePath
            //2-4 filePath Provider uri 경로 만들어준다
            val uri = FileProvider.getUriForFile(this, "com.example.cameraapppro.fileprovider", file)

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            requestLauncher.launch(intent)
        }
    }
    //이미지 비율을 계산하는 함수
    fun calculateInSampleSize(uri: Uri, reqWidth: Int, reqHight: Int) : Int{
        val option = BitmapFactory.Options()
        //inJustDecodeBounds = true 이미지 가져오지말고 이미지 정보만 줄것을 요청
        option.inJustDecodeBounds = true
        try {
            //contentResolver를 이미지 정보를 다시 가져온다
            var inputStream = contentResolver.openInputStream(uri)
            //진짜 inputStream 통해서 비트맵을 가져오는것이 아니라, 비트맵 정보만 option 저장해서 가져온다
            BitmapFactory.decodeStream(inputStream, null, option)
            inputStream?.close()
            inputStream = null
        }catch (e: java.lang.Exception){
            Log.e("MainActivity", "calculateInSampleSize inputstream ${e.printStackTrace()}")
        }
        //갤러리앱에서 가져올 실제 이미지 사이즈
        val height = option.outHeight
        val width = option.outWidth
        var inSampleSize = 1;

        if (height > reqHight || width > reqWidth){
            val halfHight = height/2
            val halfwidth = width/2
            while (halfHight / inSampleSize >= reqHight && halfwidth / inSampleSize >= reqWidth){
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}