package com.example.hw2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hw2.ui.theme.HW2Theme

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

import androidx.compose.material3.Slider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HW2Theme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val randomInitialValue = generateRandomTargetValue()

                NavHost(
                    navController = navController,
                    startDestination = "gameScreen"
                ) {
                    composable("gameScreen") {
//                        GameScreen(navController = navController, initialValue = randomInitialValue)
                        GameScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun GameScreen() {
    var targetValue by remember { mutableStateOf(generateRandomTarget()) }
    var playerGuess by remember { mutableStateOf(50f) }
    var playerScore by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Target Value: $targetValue",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        )

        Slider(
            value = playerGuess,
            onValueChange = { newValue ->
                playerGuess = newValue
            },
            valueRange = 0f..100f
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val score = calculateScore(targetValue, playerGuess)
                playerScore += score
                targetValue = generateRandomTarget()
                playerGuess = 50f
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Hit Me!")
        }

        Spacer(modifier = Modifier.height(16.dp))

                Text(
                text = "Your Score: $playerScore",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = getFeedback(calculateScore(targetValue, playerGuess)),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        )
    }
}


//@Composable
//fun GameScreen(navController: NavController, initialValue: Float) {
//    val targetValue = generateRandomTargetValue()
//    var sliderValue by remember { mutableStateOf(initialValue) }
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Text(
//            text = "Bull's Eye Game",
//            color = Color.Black,
//            fontSize = 24.sp,
//            modifier = Modifier.padding(16.dp)
//        )
//        Slider(
//            value = sliderValue,
//            onValueChange = { sliderValue = it },
//            valueRange = 0.0f..100.0f, // Set the value range of the slider
//            steps = 100,
//        )
//        Text("Slider Value: ${sliderValue}")
//
//        Button(
//            onClick = {  },
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(text = "Hit me!")
//        }
//    }
//}


fun generateRandomTargetValue(): Float {
    val random = Random.Default
    return random.nextFloat() * 100.0f
}

fun calculateScore(target: Int, guess: Float): Int {
    val difference = (target - guess).toInt()
    return when {
        difference in -3..3 -> 5
        difference in -8..8 -> 1
        else -> 0
    }
}

fun generateRandomTarget(): Int {
    return Random.nextInt(0, 101)
}

fun getFeedback(score: Int): String {
    return when (score) {
        5 -> "Perfect!"
        1 -> "Close enough."
        else -> "Try again!"
    }
}
