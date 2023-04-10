package com.example.mp3playerpro

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import com.example.mp3playerpro.databinding.ActivityPlayBinding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat

class PlayActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityPlayBinding
    val ALBUM_IMAGE_SIZE = 90
    var mediaPlayer: MediaPlayer? = null
    lateinit var musicData: MusicData
    var mp3PlayerJob: Job? = null
    var pauseFlag = false
    var currentMusicIndex: Int = 0
    var musicList: List<MusicData>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //전달해온 intent값을 가져온다
        musicData = intent.getSerializableExtra("musicData") as MusicData
        // 재생 목록 가져오기
        musicList = intent.getSerializableExtra("musicList") as? List<MusicData>
        // 현재 재생 중인 음악의 인덱스 정보 초기화
//        currentMusicIndex = musicList?.indexOf(musicData) ?: 0

        //화면에 바인딩 진행
        binding.albumTitle.text = musicData.title
        binding.albumArtist.text = musicData.artist
        binding.totalDuration.text = SimpleDateFormat("mm:ss").format(musicData.duration)
        binding.playDuration.text = "00:00"
        val bitmap = musicData.getAlbumBitmap(this, ALBUM_IMAGE_SIZE)
        if (bitmap != null) {
            binding.albumImage.setImageBitmap(bitmap)
        } else {
            binding.albumImage.setImageResource(R.drawable.music_video_24)
        }
        //음악파일객체 가져오기
        mediaPlayer = MediaPlayer.create(this, musicData.getMusicUri())
        //이벤트처리(일시정지, 실행, 돌아가기, 정지, 시크바 조절)
        binding.listButton.setOnClickListener(this)
        binding.playButton.setOnClickListener(this)
        binding.stopButton.setOnClickListener(this)
        binding.previousButton.setOnClickListener(this)
        binding.nextButton.setOnClickListener(this)
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
            R.id.listButton -> {
                mp3PlayerJob?.cancel()
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                finish()
            }
            R.id.playButton -> {
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer?.pause()
                    binding.playButton.setImageResource(R.drawable.play_24)
                    pauseFlag = true
                } else {
                    mediaPlayer?.start()
                    binding.playButton.setImageResource(R.drawable.pause_24)
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
                                binding.playButton.setImageResource(R.drawable.play_24)
                            }
                        }
                    }//end of mp3PlayerJob
                }
            }
            R.id.stopButton -> {
                mediaPlayer?.stop()
                mp3PlayerJob?.cancel()
                mediaPlayer = MediaPlayer.create(this, musicData.getMusicUri())
                binding.seekBar.progress = 0
                binding.playDuration.text = "00:00"
                binding.seekBar.max = mediaPlayer!!.duration
                binding.totalDuration.text = SimpleDateFormat("mm:ss").format(musicData.duration)
                binding.playButton.setImageResource(R.drawable.play_24)
            }
            R.id.previousButton -> {
                // 현재 재생 중인 음악의 이전 음악을 찾아서 재생
//                if (currentMusicIndex > 0) {
//                    currentMusicIndex--
//                } else {
//                    currentMusicIndex = musicList!!.size - 1
//                }
//                val previousMusic = musicList!![currentMusicIndex]
//                mediaPlayer?.stop()
//                mediaPlayer?.release()
//                mediaPlayer = MediaPlayer.create(this, previousMusic.getMusicUri())
//                musicData = previousMusic
//                binding.albumTitle.text = previousMusic.title
//                binding.albumArtist.text = previousMusic.artist
//                binding.totalDuration.text = SimpleDateFormat("mm:ss").format(previousMusic.duration)
//                val bitmap = previousMusic.getAlbumBitmap(this, ALBUM_IMAGE_SIZE)
//                if (bitmap != null) {
//                    binding.albumImage.setImageBitmap(bitmap)
//                } else {
//                    binding.albumImage.setImageResource(R.drawable.music_video_24)
//                }
//                binding.seekBar.progress = 0
//                binding.playDuration.text = "00:00"
//                binding.seekBar.max = mediaPlayer!!.duration
//                binding.playButton.setImageResource(R.drawable.pause_24)
//                mediaPlayer?.start()
            }
            R.id.nextButton -> {
                // 다음 음악을 찾아서 재생
//                if (currentMusicIndex < musicList!!.size - 1) {
//                    currentMusicIndex++
//                } else {
//                    currentMusicIndex = 0
//                }
//                val nextMusic = musicList!![currentMusicIndex]
//                mediaPlayer?.stop()
//                mediaPlayer?.release()
//                mediaPlayer = MediaPlayer.create(this, nextMusic.getMusicUri())
//                musicData = nextMusic
//                binding.albumTitle.text = nextMusic.title
//                binding.albumArtist.text = nextMusic.artist
//                binding.totalDuration.text = SimpleDateFormat("mm:ss").format(nextMusic.duration)
//                val bitmap = nextMusic.getAlbumBitmap(this, ALBUM_IMAGE_SIZE)
//                if (bitmap != null) {
//                    binding.albumImage.setImageBitmap(bitmap)
//                } else {
//                    binding.albumImage.setImageResource(R.drawable.music_video_24)
//                }
//                binding.seekBar.progress = 0
//                binding.playDuration.text = "00:00"
//                binding.seekBar.max = mediaPlayer!!.duration
//                binding.playButton.setImageResource(R.drawable.pause_24)
//                mediaPlayer?.start()
            }
            R.id.likeButton -> {

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

}