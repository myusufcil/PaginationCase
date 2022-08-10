package com.myusufcil.casestudy.ui.main

import androidx.activity.viewModels
import com.myusufcil.casestudy.base.BaseActivity
import com.myusufcil.casestudy.databinding.ActivityMainBinding
import com.myusufcil.casestudy.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(ActivityMainBinding::inflate){

    override val viewModel: MainViewModel by viewModels()

}