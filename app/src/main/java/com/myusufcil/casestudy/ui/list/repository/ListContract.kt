package com.myusufcil.casestudy.ui.list.repository

import androidx.paging.PagingData
import com.myusufcil.casestudy.response.Person
import kotlinx.coroutines.flow.Flow

interface ListContract {

    interface Repository {
        suspend fun getPersonList(): Flow<PagingData<Person>>
    }
}