package com.example.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.MyViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentUserBinding

class UserFragment : Fragment() {
    private val mBinding: FragmentUserBinding by lazy { FragmentUserBinding.inflate(layoutInflater) }
    private val userViewModel by lazy {
        ViewModelProvider(this).get(MyViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onClick()

    }

    private fun onClick() {
        mBinding.btnUser.setOnClickListener {
            userViewModel.UserSendRequest()
            userViewModel.userData.observe(this, Observer { data ->
                mBinding.tvUser.text = data.toString()
            })

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return mBinding.root
    }

}