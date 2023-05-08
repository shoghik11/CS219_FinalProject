package com.example.randomusergenerator.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.randomusergenerator.data.model.User

@Composable
fun UserDetailsScreen(navController: NavController, user: User) {
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "User Detail", fontWeight = FontWeight.Bold)
            }
        }
    }) {it
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = user.picture.large,
                contentDescription = "avatar",
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(30.dp))
                    .height(100.dp)
                    .width(100.dp)
            )

            Text(text = user.name.title)
            Text(text = user.name.first)
            Text(text = user.name.last)
        }
    }
}