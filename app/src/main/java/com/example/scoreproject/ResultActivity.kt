package com.example.scoreproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoreproject.databinding.ActivityResultBinding
import com.example.whatsmyscore.list.ListAdapter
import com.example.whatsmyscore.list.ListLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ResultActivity : AppCompatActivity() {

    //뷰바인딩
    private var mBinding: ActivityResultBinding? = null
    private val binding get() = mBinding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //뷰바인딩
        mBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore

        val itemList = arrayListOf<ListLayout>()
        val adapter = ListAdapter(itemList)

        binding.recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = adapter

        binding.readBtn.setOnClickListener {
            db.collection("score")
                .get()
                .addOnSuccessListener { result ->
                    itemList.clear()
                    for (document in result) {
                        val item = ListLayout(document["name"] as? String, document["score"] as? String, document["date"] as? String)
                        itemList.add(item)
                    }
                    adapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "Error getting documents: $exception")
                }
        }
    }

    //뷰바인딩
    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}