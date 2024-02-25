package com.example.littlelemon.onboarding.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun UpperPanel() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LittleLemonColor.green)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 42.dp, bottom = 42.dp)
                .background(LittleLemonColor.green),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.onboarding_welcome),
                fontSize = 32.sp,
                color = LittleLemonColor.cloud,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UpperPanelPreview() {
    UpperPanel()
}
