package com.example.scoreproject

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.scoreproject.databinding.ActivityScoreBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ScoreActivity : AppCompatActivity() {
    //뷰바인딩
    private var mBinding: ActivityScoreBinding? = null
    private val binding get() = mBinding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = Firebase.firestore


        val checkBtn = binding.checkButton

        // 학년 드랍다운
        val grade = resources.getStringArray(R.array.grade)
        val gradeArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, grade)
        binding.autoComplete.setAdapter(gradeArrayAdapter)
        // 반 드랍다운
        val classNum = resources.getStringArray(R.array.classNum)
        val classArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, classNum)
        binding.classAutoComplete.setAdapter(classArrayAdapter)

        //document 이름 만들기
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd:HHmm")
        val formatted = current.format(formatter)
        val docName = "doc${formatted}"

        checkBtn.setOnClickListener {
            val studentVal = binding.studentName.text.toString()
            val reasonVal = binding.reason.text.toString()
            val giverNameVal = binding.giverName.text.toString()
            val scoreVal = binding.score.text.toString()
            val gradeVal = binding.autoComplete.text.toString()
            val classVal = binding.classAutoComplete.text.toString()

            val formData = hashMapOf(
                "name" to studentVal,
                "reason" to reasonVal,
                "giverName" to giverNameVal,
                "score" to scoreVal,
                "grade" to gradeVal,
                "classNum" to classVal,
            )
            db.collection("score").document(docName)
                .set(formData)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    onBackPressed()
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }


    }
    //뷰바인딩
    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}