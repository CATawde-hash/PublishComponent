package com.example.publishcomponent

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun tmTextView(){

    Text("Hi, I am from shared" , color = Color.Blue, modifier = Modifier.background(Color.Yellow))
}