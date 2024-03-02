package com.example.littlelemon.screens.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DishDetails(id: Int, navController: NavController) {
    /*val dish = requireNotNull(DishRepository.getDish(id))
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        TopAppBarNoProfile(navController = navController)
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            Image(
                painter = painterResource(id = dish.imageResource),
                contentDescription = dish.name,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Column {
                Text(
                    text = dish.name,
                    style = MaterialTheme.typography.h1
                )
                Text(
                    text = dish.description,
                    style = MaterialTheme.typography.body1
                )
            }
            Counter()
            Button(
                onClick = { *//*TODO*//* },
                colors = ButtonDefaults.buttonColors(backgroundColor = LittleLemonColor.yellow)
            ) {
                Text(
                    text = stringResource(id = R.string.add_for) + " $${dish.price}",
                    textAlign = TextAlign.Center
                )
            }
        }
    }*/
}

@Composable
fun Counter() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        var counter by remember {
            mutableStateOf(1)
        }
        TextButton(
            onClick = {
                counter--
            }
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.h2
            )
        }
        Text(
            text = counter.toString(),
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(16.dp)
        )
        TextButton(
            onClick = {
                counter++
            }
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.h2
            )
        }
    }
}

@Preview
@Composable
fun DishDetailsPreview() {
    DishDetails(id = 1, rememberNavController())
}
