package com.example.scoreproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scoreproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //뷰바인딩
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //뷰바인딩
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movePage()
    }
    private fun movePage() {
        binding.giveScoreButton.setOnClickListener{
            startActivity(Intent(this, ScoreActivity::class.java))
        }
    }


    //뷰바인딩
    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}