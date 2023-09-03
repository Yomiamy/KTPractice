package com.ktpractice.flow.main.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ktpractice.api.interfaces.IApi
import com.ktpractice.db.PersonDao
import com.ktpractice.model.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class PersonPagingSource(private val mDao: PersonDao, private val mIApi: IApi) :
    PagingSource<Int, Person>() {

    companion object {
        private const val TAG = "PersonPagingSource"
    }

    var teamName: String = ""

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> = withContext(Dispatchers.IO) {
        try {
            // Start refresh at page 1 if undefined.
            var nextPageNumber: Int? = params.key ?: 0

            Log.d(TAG, "$teamName Page = $nextPageNumber")
            val response = mIApi.search(teamName.lowercase(), nextPageNumber!!)

            if (!response.results.isNullOrEmpty()) {
                response.results!!.forEach {
                    it.teamName = teamName
                }
                mDao.insertAll(response.results!!)
                ++nextPageNumber
            } else {
                nextPageNumber = null
            }

            return@withContext LoadResult.Page(
                data = response.results!!,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: IOException) {
            return@withContext LoadResult.Error(e)
        }
    }
}