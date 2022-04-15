package com.jetpack.placeholderhintanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.placeholderhintanimation.ui.theme.PlaceholderHintAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaceholderHintAnimationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Animated Placeholder",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            PlaceHolderHintAnimation()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceHolderHintAnimation() {
    val inputField = remember { mutableStateOf(TextFieldValue()) }

    OutlinedTextField(
        value = inputField.value,
        onValueChange = { inputField.value = it },
        placeholder = {
            AnimatedPlaceholder(
                hints = listOf(
                    "Search your favourite food",
                    "Search your favourite youtube channel",
                    "Search your favourite topic"
                )
            )
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedPlaceholder(
    hints: List<String>,
    textColor: Color = Color.Gray
) {
    val iterator = hints.listIterator()
    val target by produceState(
        initialValue = hints.first()
    ) {
        iterator.doWhenHasNextOrPrevious {
            value = it
        }
    }

    AnimatedContent(
        targetState = target,
        transitionSpec = {
            ScrollAnimation()
        }
    ) { text ->
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontFamily = FontFamily.Serif
        )
    }
}




















