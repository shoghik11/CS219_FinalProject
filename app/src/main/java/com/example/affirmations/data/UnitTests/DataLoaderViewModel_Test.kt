package com.example.affirmations.data.UnitTests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.affirmations.DataLoaderViewModel
import com.example.affirmations.data.ArticleResponse
import com.example.affirmations.data.NewsApiService
import com.example.affirmations.data.NewsRepo
import io.mockk.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DataLoaderViewModel_Test {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DataLoaderViewModel
    private lateinit var mockNewsRepo: NewsRepo
    private lateinit var mockNewsApiService: NewsApiService

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    var articlesObserver = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        mockNewsRepo = mockk()
        mockNewsApiService = mockk()
        viewModel = DataLoaderViewModel()
        viewModel.articles.observeForever(articlesObserver)
    }

    @After
    fun teardown() {
        viewModel.articles.removeObserver(articlesObserver)
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
        unmockkAll()
    }
    val source1 = null
    val source2 = null
    @Test
    fun `test getPosts success`() = runBlocking {
        val response = listOf(
            ArticleResponse(source1, "author1", "title1", "url1"),
            ArticleResponse(source2, "author2", "title2", "url2")
        )

        coEvery { mockNewsRepo.fetchNews(any()) } returns response

        // When
        viewModel.getPosts()

        // Then
        coVerify { mockNewsRepo.fetchNews("us") }
        verify { articlesObserver.onChanged(Result.loading()) }
        verify { articlesObserver.onChanged(Result.success(response)) }
    }

    @Test
    fun `test getPosts error`() = runBlocking {
        val errorMessage = "Error fetching news"

        coEvery { mockNewsRepo.fetchNews(any()) } throws Exception(errorMessage)

        viewModel.getPosts()

        coVerify { mockNewsRepo.fetchNews("us") }
        verify { articlesObserver.onChanged(Result.loading()) }
        verify { articlesObserver.onChanged(Result.error(any())) }
    }

    @Test
    fun `test getPostsWithCategory success`() = runBlocking {
        // Given
        val category = "business"
        val query = "finance"
        val response = listOf(
            ArticleResponse(source1, "author1", "title1", "url1"),
            ArticleResponse(source2, "author2", "title2", "url2")
        )

        coEvery { mockNewsRepo.fetchNewsWithCategory(any(), any(), any()) } returns response

        viewModel.getPostsWithCategory(category, query)

        coVerify { mockNewsRepo.fetchNewsWithCategory("us", category, query) }
        verify { articlesObserver.onChanged(Result.success(response)) }
    }

    @Test
    fun `test getPostsWithCategory no data`() = runBlocking {
        // Given
        val category = "sports"
        val query = "football"
        val response = emptyList<ArticleResponse>()

        coEvery { mockNewsRepo.fetchNewsWithCategory(any(), any(), any()) } returns response

        // When
        viewModel.getPostsWithCategory(category, query)

        // Then
        coVerify { mockNewsRepo.fetchNewsWithCategory("us", category, query) }
        verify { articlesObserver.onChanged(Result.error(any())) }
    }

}
sealed class Result<out T : Any> {
    data class Loading(val message: String = "") : Result<Nothing>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    companion object {
        fun <T : Any> loading(message: String = ""): Result<T> = Loading(message)
        fun <T : Any> success(data: T): Result<T> = Success(data)
        fun error(exception: Exception): Result<Nothing> = Error(exception)
    }
}