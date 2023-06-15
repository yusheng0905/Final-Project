package com.ncueel.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ncueel.finalproject.databinding.FragmentMemberBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [memberFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class memberFragment : Fragment() {
    //Step 1: 初始化FirebaseAuth
    private lateinit var auth: FirebaseAuth

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_member, container, false)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //設計logout button功能
        //Step 2-1: 初始化FirebaseAuth->初始化auth
        auth = Firebase.auth
        val logout = view.findViewById<Button>(R.id.button3)
        logout.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
        }

        //查看訂單
        val checkOrders = view.findViewById<TextView>(R.id.textView11)
        checkOrders.setOnClickListener{
            startActivity(Intent(requireContext(),MainActivityOrders::class.java))
        }

        //更改密碼
        val changePassword = view.findViewById<TextView>(R.id.textView17)
        changePassword.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivityChangePW::class.java))
        }

        //個人檔案
        val memberInfo = view.findViewById<TextView>(R.id.textView18)
        memberInfo.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivityMemberInfo::class.java))
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment memberFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            memberFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}