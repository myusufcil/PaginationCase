package com.myusufcil.casestudy.ui.list.datasource

import DataSource
import FetchError
import FetchResponse
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myusufcil.casestudy.constants.AppConstants.NETWORK_PAGE_SIZE
import com.myusufcil.casestudy.response.Person

private const val STARTING_PAGE_INDEX = 1

class PersonPagingDataSource : PagingSource<Int, Person>() {

    private var fetchResponse: FetchResponse? = FetchResponse()
    private var fetchError: FetchError? = FetchError()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        DataSource().fetch(pageIndex.toString()) { _fetchResponse, _fetchError ->
            fetchResponse =
                _fetchResponse.takeIf { true } ?: let { _fetchResponse }
            fetchError = _fetchError.takeIf { true } ?: let { _fetchError }
        }
        return try {
            val nextKey = if (fetchResponse?.people?.isEmpty() == true)
                null
            else
                pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
            LoadResult.Page(
                data = fetchResponse?.people ?: emptyList(),
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}