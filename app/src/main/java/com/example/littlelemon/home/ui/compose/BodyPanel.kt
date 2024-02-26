package com.example.littlelemon.home.ui.compose

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.R
import com.example.littlelemon.home.data.Dish
import com.example.littlelemon.home.data.DishCategory
import com.example.littlelemon.nav.DishDetails
import com.example.littlelemon.ui.theme.LittleLemonColor

data class HomeBodyScreenState(
    val searchText: String,
    val onSearchTextChange: (String) -> Unit,
    val isSearching: Boolean,
    val dishes: List<Dish>,
    val categories: List<DishCategory>,
    val onCategorySelected: (String) -> Unit
)

@Composable
fun BodyPanel(
    navController: NavHostController,
    homeBodyScreenState: HomeBodyScreenState
) {
    with(homeBodyScreenState) {
        LazyColumn {
            item {
                UpperPanel(
                    searchText,
                    onSearchTextChange
                )
            }
            item {
                CategorySection(
                    categories = categories,
                    onCategorySelected = onCategorySelected
                )
            }
            item {
                if (isSearching) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    Column {
                        dishes.forEach { dish ->
                            MenuDish(navController, dish)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UpperPanel(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LittleLemonColor.green)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
                .background(LittleLemonColor.green)
        ) {
            Text(
                text = stringResource(id = R.string.title),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = LittleLemonColor.yellow
            )
            Text(
                text = stringResource(id = R.string.location),
                fontSize = 24.sp,
                color = LittleLemonColor.cloud
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.description),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(bottom = 28.dp, end = 20.dp)
                        .fillMaxWidth(0.6f),
                    color = LittleLemonColor.cloud,
                    fontWeight = FontWeight.Normal
                )
                Image(
                    painter = painterResource(id = R.drawable.upperpanelimage),
                    contentDescription = "Upper Panel Image",
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                )
            }
            TextField(
                singleLine = true,
                value = searchText,
                onValueChange = {
                    onSearchTextChange.invoke(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                placeholder = { Text(text = "Enter Search Phrase") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search_24),
                        tint = LittleLemonColor.charcoal,
                        contentDescription = "Search button"
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = LittleLemonColor.charcoal, // Color of the border when the field is in focus
                    unfocusedIndicatorColor = LittleLemonColor.charcoal, // Color of the border when the field is not in focus
                    cursorColor = LittleLemonColor.charcoal,
                    backgroundColor = LittleLemonColor.cloud
                )
            )
        }
    }
}

@Composable
fun CategorySection(
    categories: List<DishCategory>,
    onCategorySelected: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = stringResource(R.string.order_for_delivery).uppercase(),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                items(categories) {
                    OutlinedButton(
                        onClick = {
                            onCategorySelected.invoke(it.name)
                        },
                        border = BorderStroke(1.dp, LittleLemonColor.cloud),
                        shape = RoundedCornerShape(50), // = 50% percent
                        // or shape = CircleShape
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = if (it.isSelected) LittleLemonColor.yellow else LittleLemonColor.cloud,
                            contentColor = LittleLemonColor.charcoal
                        )
                    ) {
                        Text(text = it.name)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuDish(navController: NavHostController? = null, dish: Dish) {
    Card(onClick = {
        Log.d("AAA", "Click ${dish.id}")
        navController?.navigate(DishDetails.route + "/${dish.id}")
    }) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column {
                    Text(
                        text = dish.name,
                        style = MaterialTheme.typography.h2
                    )
                    Text(
                        text = dish.description,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "$${dish.price}",
                        style = MaterialTheme.typography.body2,
                    )
                }
                Image(
                    painter = painterResource(id = dish.imageResource),
                    contentDescription = dish.name,
                    modifier = Modifier.clip(
                        RoundedCornerShape(1.dp)
                    )
                )
            }
            Divider(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                thickness = 1.dp,
                color = LittleLemonColor.cloud
            )
        }
    }
}
