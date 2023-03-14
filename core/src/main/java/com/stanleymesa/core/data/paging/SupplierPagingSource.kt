package com.stanleymesa.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stanleymesa.core.data.source.remote.network.ApiServices
import com.stanleymesa.core.domain.model.Supplier
import com.stanleymesa.core.utlis.DataMapper

class SupplierPagingSource(
    private val token: String,
    private val apiServices: ApiServices,
) : PagingSource<Int, Supplier>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Supplier> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX

            val request = apiServices.getSupplier(
                token = token,
                offset = position,
                limit = params.loadSize
            )

            if (request.isSuccessful) {
                val response = request.body()!!
                LoadResult.Page(
                    data = response.data.map {
                        DataMapper.mapSupplierNotNullToSupplier(it)
                    },
                    prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                    nextKey = if (response.data.isEmpty()) null else position + 1
                )
            } else {
                LoadResult.Error(Throwable(request.code().toString()))
            }

        } catch (ex: Exception) {
            return LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Supplier>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}