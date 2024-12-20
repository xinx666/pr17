package com.example.taxi

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun History(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(onClose = { scope.launch { drawerState.close() } }, navController)
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(Color(0xFF2A2E43), RoundedCornerShape(topEnd = 0.dp, bottomEnd = 0.dp))
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Image(
                                painter = painterResource(id = R.drawable.history),
                                contentDescription = "Open Menu",
                                modifier = Modifier.size(50.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "История",
                            color = Color.White,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Start
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 30.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Kia Rio", color = Color.Black, fontSize = 25.sp)
                            Text(text = "$15", color = Color.Black, fontSize = 25.sp)
                        }
                        Text(text = "15 мин", color = Color.Gray, fontSize = 20.sp)

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Kia Rio", color = Color.Black, fontSize = 25.sp)
                            Text(text = "$10", color = Color.Black, fontSize = 25.sp)
                        }
                        Text(text = "10 мин", color = Color.Gray, fontSize = 20.sp)

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Kia Rio", color = Color.Black, fontSize = 25.sp)
                            Text(text = "$50", color = Color.Black, fontSize = 25.sp)
                        }
                        Text(text = "60 мин", color = Color.Gray, fontSize = 20.sp)
                    }
                }
            }
        }
    )
}

@Composable
fun DrawerContent(onClose: () -> Unit, navController: NavController) {
    Column(
        modifier = Modifier
            .width(250.dp)
            .fillMaxHeight()
            .background(Color(0xFF2A2E43), RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.End)
                .size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Close Menu",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.height(60.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(40.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Ivanov Ivan\nDriver",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        }

        MenuItem(
            imageResId = R.drawable.settings,
            text = "Настройки",
            onClick = {
                onClose()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        MenuItem(
            imageResId = R.drawable.history,
            text = "История",
            onClick = {
                navController.navigate("History")
            }
        )
    }
}

@Composable
fun MenuItem(@DrawableRes imageResId: Int, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = text,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
