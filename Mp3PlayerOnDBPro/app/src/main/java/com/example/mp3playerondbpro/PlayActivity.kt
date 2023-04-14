package com.example.mp3playerondbpro

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.SeekBar
import com.example.mp3playerondbpro.databinding.ActivityPlayBinding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat

class PlayActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityPlayBinding
    val ALBUM_IMAGE_SIZE = 90
    var mediaPlayer: MediaPlayer? = null
    lateinit var musicData: MusicData
    var mp3PlayerJob: Job? = null
    var pauseFlag = false
    private var playList: MutableList<Parcelable>? = null
    private var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //전달해온 intent값을 가져온다
//        musicData = intent.getSerializableExtra("musicData") as MusicData
        playList = intent.getParcelableArrayListExtra("parcelableList")
        currentPosition = intent.getIntExtra("position", 0)
        musicData = playList?.get(currentPosition) as MusicData

        //화면에 바인딩 진행
        binding.tvTitle.text = musicData.title
        binding.tvArtist.text = musicData.artist
        binding.totalDuration.text = SimpleDateFormat("mm:ss").format(musicData.duration)
        binding.playDuration.text = "00:00"
        val bitmap = musicData.getAlbumBitmap(this, ALBUM_IMAGE_SIZE)
        if (bitmap != null) {
            binding.ivMusic.setImageBitmap(bitmap)
        } else {
            binding.ivMusic.setImageResource(R.drawable.music_player)
        }
        //음악파일객체 가져오기
        mediaPlayer = MediaPlayer.create(this, musicData.getMusicUri())
        //이벤트처리(일시정지, 실행, 돌아가기, 정지, 시크바 조절)
        binding.btnList.setOnClickListener(this)
        binding.btnPlay.setOnClickListener(this)
        binding.btnStop.setOnClickListener(this)
        binding.btnPrevious.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.btnSuffle.setOnClickListener(this)
        binding.seekBar.max = mediaPlayer!!.duration
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnList -> {
                mp3PlayerJob?.cancel()
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                finish()
            }
            R.id.btnPlay -> {
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer?.pause()
                    binding.btnPlay.setImageResource(R.drawable.play)
                    pauseFlag = true
                } else {
                    mediaPlayer?.start()
                    binding.btnPlay.setImageResource(R.drawable.pause)
                    pauseFlag = false

                    //코루틴으로 음악을 재생
                    val backgroundScope = CoroutineScope(Dispatchers.Default + Job())
                    mp3PlayerJob = backgroundScope.launch {
                        while (mediaPlayer!!.isPlaying) {
                            var currentPosition = mediaPlayer?.currentPosition!!
                            //코루틴속에서 화면의 값을 변동시키고자 할때 runOnUiThread
                            runOnUiThread {
                                binding.seekBar.progress = currentPosition
                                binding.playDuration.text =
                                    SimpleDateFormat("mm:ss").format(mediaPlayer?.currentPosition)
                            }
                            try {
                                delay(1000)
                            } catch (e: java.lang.Exception) {
                                Log.e("PlayActivity", "delay 오류발생 ${e.printStackTrace()}")
                            }
                        }
                        if (pauseFlag == false) {
                            runOnUiThread {
                                binding.seekBar.progress = 0
                                binding.playDuration.text = "00:00"
                                binding.btnPlay.setImageResource(R.drawable.play)
                            }
                        }
                    }//end of mp3PlayerJob
                }
            }
            R.id.btnStop -> {
                mediaPlayer?.stop()
                mp3PlayerJob?.cancel()
                mediaPlayer = MediaPlayer.create(this, musicData.getMusicUri())
                binding.seekBar.progress = 0
                binding.playDuration.text = "00:00"
                binding.seekBar.max = mediaPlayer!!.duration
                binding.totalDuration.text = SimpleDateFormat("mm:ss").format(musicData.duration)
                binding.btnPlay.setImageResource(R.drawable.play)
            }
            R.id.btnPrevious -> {
                mp3PlayerJob?.cancel()
                mediaPlayer?.stop()
                binding.seekBar.progress = 0
                binding.playDuration.text = "00:00"

                val previousCurrentPosition = currentPosition - 1
                if (previousCurrentPosition >= 0) {
                    currentPosition = previousCurrentPosition
                    musicData = playList?.get(currentPosition) as MusicData
                } else {
                    // 이전 곡이 없으므로 마지막 곡을 재생
                    currentPosition = playList!!.size - 1
                    musicData = playList?.get(currentPosition) as MusicData
                }

                mediaPlayer = MediaPlayer.create(this, musicData.getMusicUri())
                binding.tvTitle.text = musicData.title
                binding.tvArtist.text = musicData.artist
                binding.totalDuration.text = SimpleDateFormat("mm:ss").format(musicData.duration)
                binding.seekBar.max = mediaPlayer!!.duration
                binding.btnPlay.setImageResource(R.drawable.play)
                mediaPlayer?.start()

                //코루틴으로 음악을 재생
                val backgroundScope = CoroutineScope(Dispatchers.Default + Job())
                mp3PlayerJob = backgroundScope.launch {
                    while (mediaPlayer!!.isPlaying) {
                        var currentPosition = mediaPlayer?.currentPosition!!
                        //코루틴속에서 화면의 값을 변동시키고자 할때 runOnUiThread
                        runOnUiThread {
                            binding.seekBar.progress = currentPosition
                            binding.playDuration.text =
                                SimpleDateFormat("mm:ss").format(mediaPlayer?.currentPosition)
                        }
                        try {
                            delay(1000)
                        } catch (e: java.lang.Exception) {
                            Log.e("PlayActivity", "delay 오류발생 ${e.printStackTrace()}")
                        }
                    }
                    if (pauseFlag == false) {
                        runOnUiThread {
                            binding.seekBar.progress = 0
                            binding.playDuration.text = "00:00"
                            binding.btnPlay.setImageResource(R.drawable.pause)
                        }
                    }
                }
            }
            R.id.btnNext -> {
                mp3PlayerJob?.cancel()
                mediaPlayer?.stop()
                binding.seekBar.progress = 0
                binding.playDuration.text = "00:00"

                val nextCurrentPosition = currentPosition + 1
                if (nextCurrentPosition < playList!!.size) {
                    currentPosition = nextCurrentPosition
                    musicData = playList?.get(currentPosition) as MusicData
                } else {
                    // 다음 곡이 없으므로 첫번째 곡을 재생
                    currentPosition = 0
                    musicData = playList?.get(currentPosition) as MusicData
                }

                mediaPlayer = MediaPlayer.create(this, musicData.getMusicUri())
                binding.tvTitle.text = musicData.title
                binding.tvArtist.text = musicData.artist
                binding.totalDuration.text = SimpleDateFormat("mm:ss").format(musicData.duration)
                binding.seekBar.max = mediaPlayer!!.duration
                binding.btnPlay.setImageResource(R.drawable.play)
                mediaPlayer?.start()
                //코루틴으로 음악을 재생
                val backgroundScope = CoroutineScope(Dispatchers.Default + Job())
                mp3PlayerJob = backgroundScope.launch {
                    while (mediaPlayer!!.isPlaying) {
                        var currentPosition = mediaPlayer?.currentPosition!!
                        //코루틴속에서 화면의 값을 변동시키고자 할때 runOnUiThread
                        runOnUiThread {
                            binding.seekBar.progress = currentPosition
                            binding.playDuration.text =
                                SimpleDateFormat("mm:ss").format(mediaPlayer?.currentPosition)
                        }
                        try {
                            delay(1000)
                        } catch (e: java.lang.Exception) {
                            Log.e("PlayActivity", "delay 오류발생 ${e.printStackTrace()}")
                        }
                    }
                    if (pauseFlag == false) {
                        runOnUiThread {
                            binding.seekBar.progress = 0
                            binding.playDuration.text = "00:00"
                            binding.btnPlay.setImageResource(R.drawable.pause)
                        }
                    }
                }
            }
            R.id.ivItemLike -> {

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer?.stop()
        mp3PlayerJob?.cancel()
        mediaPlayer?.release()
        mediaPlayer = null
        finish()
    }
    
    fun playMusic(){
        
    }
    
    fun setMusic(){
        
    }
}