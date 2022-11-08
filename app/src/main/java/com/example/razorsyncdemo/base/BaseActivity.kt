package com.example.razorsyncdemo.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VB : ViewDataBinding>(
    val bindingFactory: (LayoutInflater) -> VB
) : AppCompatActivity() {

    private var mViewBinding: VB? = null
    val binding get() = mViewBinding!!

    open fun setUpView() {}
    open fun observeData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        setUpView()
        observeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding = null
    }
}