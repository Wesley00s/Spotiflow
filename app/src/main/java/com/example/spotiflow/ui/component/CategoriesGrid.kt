package com.example.spotiflow.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState 
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState 
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spotiflow.data.model.UiCategory

@Composable
fun CategoriesGrid(
    categories: List<UiCategory>,
    onCategoryClick: (UiCategory) -> Unit,
    gridState: LazyGridState, 
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        state = gridState, 
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            CategoryCard(
                category = category,
                onClick = onCategoryClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesGridPreview() {
    val sampleCategories = listOf(
        UiCategory("rock", "Rock", "https://t.scdn.co/images/728ed47fc1674feb95f7ac20236eb6d7.jpeg"),
        UiCategory("pop", "Pop", "https://t.scdn.co/images/728ed47fc1674feb95f7ac20236eb6d7.jpeg"),
        UiCategory("classical", "Classical", "https://t.scdn.co/images/728ed47fc1674feb95f7ac20236eb6d7.jpeg"),
        UiCategory("metal", "Metal", "https://t.scdn.co/images/728ed47fc1674feb95f7ac20236eb6d7.jpeg"),
        UiCategory("jazz", "Jazz", "https://t.scdn.co/images/728ed47fc1674feb95f7ac20236eb6d7.jpeg"),
        UiCategory("hip-hop", "Hip-Hop", "https://t.scdn.co/images/728ed47fc1674feb95f7ac20236eb6d7.jpeg"),
    )
    CategoriesGrid(
        categories = sampleCategories,
        onCategoryClick = {},
        gridState = rememberLazyGridState() 
    )
}