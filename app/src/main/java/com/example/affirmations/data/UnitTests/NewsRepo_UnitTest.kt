package com.example.affirmations.data.UnitTests

import com.example.affirmations.data.*
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class NewsRepo_UnitTest {
    private lateinit var apiService: NewsApiService
    private lateinit var newsService: NewsRepo

    @Before
    fun setup() {
        apiService = mock(NewsApiService::class.java)
        newsService = NewsRepo(apiService)
    }

    @Test
    suspend fun testFetchNews() {
        val expectedCountry = "us"

        val mockResponse = NewsResponse(
            status = "success",
            totalResults = 2,
            articles = listOf(
                ArticleResponse(
                    source = SourceResponse("source1", "Source 1"),
                    author = "John Doe",
                    title = "Article 1",
                    urlToImage = "https://example.com/image1.jpg"
                ),
                ArticleResponse(
                    source = SourceResponse("source2", "Source 2"),
                    author = "Jane Smith",
                    title = "Article 2",
                    urlToImage = "https://example.com/image2.jpg"
                )
            )
        )

        `when`(apiService.fetchNews(expectedCountry)).thenReturn(mockResponse)

        val result = newsService.fetchNews(expectedCountry)

        assertEquals(mockResponse.articles.size, result.size)
        assertEquals(mockResponse.articles[0].title, result[0].title)
        assertEquals(mockResponse.articles[1].author, result[1].author)
    }
}