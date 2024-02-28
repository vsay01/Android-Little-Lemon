package com.example.littlelemon.screens.splash.ui

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.datastore.UserPreferences
import com.example.littlelemon.datastore.isUserPreferencesEmpty
import com.example.littlelemon.nav.Home
import com.example.littlelemon.nav.Onboarding
import com.example.littlelemon.nav.Splash
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SplashScreen(navController: NavController, userPreferencesStateFlow: StateFlow<UserPreferences>) = Box(
    Modifier
        .fillMaxWidth()
        .fillMaxHeight()
) {

    val userPreferences by userPreferencesStateFlow.collectAsState()

    val scale = remember {
        androidx.compose.animation.core.Animatable(0.0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )
        delay(1000)
        navController.navigate(
            if (userPreferences.isUserPreferencesEmpty())
                Onboarding.route
            else
                Home.route
        ) {
            popUpTo(Splash.route) {
                inclusive = true
            }
        }
    }

    Image(
        painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
        contentDescription = "",
        alignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
            .scale(scale.value)
    )
}