package com.myusufcil.casestudy.ui.list.repository

import DataSource
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import com.myusufcil.casestudy.constants.AppConstants.NETWORK_PAGE_SIZE
import com.myusufcil.casestudy.response.Person
import com.myusufcil.casestudy.ui.list.datasource.PersonPagingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListRepository @Inject constructor() : ListContract.Repository {

    override suspend fun getPersonList(): Flow<PagingData<Person>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                PersonPagingDataSource()
            }
        ).flow.map {
            val personMap = mutableSetOf<Int>()
            it.filter { person ->
                if (personMap.contains(person.id)) {
                    false
                } else {
                    personMap.add(person.id)
                }
            }
        }
}
