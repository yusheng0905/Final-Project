package com.ncueel.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ncueel.finalproject.databinding.FragmentMemberBinding

class memberFragment : Fragment() {
    //Step 1: 初始化FirebaseAuth
    private lateinit var auth: FirebaseAuth

    //Step 1:先從註冊這個功能開始寫->先初始化一個資料鏈結的部分
    private lateinit var binding: FragmentMemberBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Step 4: 設計logout button功能
        binding.button3.setOnClickListener {
            auth.signOut()
        }
    }
}