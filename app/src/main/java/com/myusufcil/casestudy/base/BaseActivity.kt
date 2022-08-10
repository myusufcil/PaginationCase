package com.myusufcil.casestudy.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

typealias Inflater<T> = (LayoutInflater) -> T

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel>(private val inflate: Inflater<VB>) :
    AppCompatActivity() {

    abstract val viewModel: VM

    private var _binding: VB? = null
    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = inflate.invoke(layoutInflater)
        setContentView(binding?.root)
        viewModel.handleIntent(intent)
    }
}