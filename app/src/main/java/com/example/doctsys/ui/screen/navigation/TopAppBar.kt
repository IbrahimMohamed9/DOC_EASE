package com.example.doctsys.ui.screen.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    titleScreen: String,
    canNavigateBack: Boolean,
    navigateBack: () -> Unit = {}
){
    CenterAlignedTopAppBar(
        title = {Text(titleScreen)},
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        navigationIcon = {
            if (canNavigateBack){
                IconButton(onClick = navigateBack ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun TopAppBarPreview(){
    TopAppBar(titleScreen = "test", canNavigateBack = true, {})
}