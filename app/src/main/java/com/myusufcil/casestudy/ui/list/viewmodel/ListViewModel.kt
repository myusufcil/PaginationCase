package com.myusufcil.casestudy.ui.list.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myusufcil.casestudy.base.BaseViewModel
import com.myusufcil.casestudy.response.Person
import com.myusufcil.casestudy.ui.list.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: ListRepository
) : BaseViewModel() {

    suspend fun getPersonList(): Flow<PagingData<Person>> =
        repository.getPersonList().cachedIn(viewModelScope)

}