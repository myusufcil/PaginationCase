package com.myusufcil.casestudy.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    open fun handleIntent(intent: Intent) {}
    open fun handleArguments(argument: Bundle) {}
}