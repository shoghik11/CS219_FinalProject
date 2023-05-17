package com.example.affirmations.data.UnitTests

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import com.example.affirmations.DataLoaderViewModel
import com.example.affirmations.NewsScreen
import com.example.affirmations.data.ArticleResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test


class NewsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun newsScreen_contentDisplayed() {
        val source1 = null
        val source2=null
        val source3=null

        val articles = listOf(
            ArticleResponse(source1, "author1", "title1", "urlToImage1"),
            ArticleResponse(source2, "author2", "title2", "urlToImage2"),
            ArticleResponse(source3, "author3", "title3", "urlToImage3")
        )
        val navController = mockk<NavHostController>(relaxed = true)

        composeTestRule.setContent {
            MaterialTheme {
                NewsScreen(navController = navController, viewModel = mockViewModel(articles))
            }
        }

        composeTestRule.onNodeWithText("NEWS").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()
        composeTestRule.onNodeWithText("Business").assertIsDisplayed()
        composeTestRule.onNodeWithText("Entertainment").assertIsDisplayed()
        composeTestRule.onNodeWithText("General").assertIsDisplayed()
        composeTestRule.onNodeWithText("Health").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Search").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Filter icon").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ArticleList").assertIsDisplayed()
        composeTestRule.onAllNodesWithText("title").assertCountEquals(articles.size)
    }

    private fun mockViewModel(articles: List<ArticleResponse>): DataLoaderViewModel {
        val viewModel = mockk<DataLoaderViewModel>()
        every { viewModel.articles } returns Result.Success(articles)

        return viewModel
    }
}