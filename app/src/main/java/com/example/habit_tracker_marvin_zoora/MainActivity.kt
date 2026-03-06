package com.example.habit_tracker_marvin_zoora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//i use a data class for a habit and boolean whether it's completed
data class Habit(
    val name: String,
    var completed: Boolean = false
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HabitTrackerApp()
        }
    }
}

@Composable
fun HabitTrackerApp() {
    //text entered by user
    var habitText by remember { mutableStateOf("") }
    // list to store habits
    val habitList = remember {
        mutableStateListOf<Habit>()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Student Habit Tracker",
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(15.dp))

        //input section to add a habit
        HabitInputSection(
            habitText = habitText,
            onTextChange = { habitText = it },
            onAddClick = {
                if (habitText.isNotBlank()) {
                    habitList.add(Habit(habitText))
                    habitText = ""
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
            //display list of habits
        HabitList(
            habits = habitList
        )
    }
}
//composable for habit input section
@Composable
fun HabitInputSection(
    habitText: String,
    onTextChange: (String) -> Unit,
    onAddClick: () -> Unit
) {

    Column {

        Text("Enter a new habit")

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = habitText,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onAddClick) {
            Text("Add Habit")
        }
    }
}
//dispaly list of habits
@Composable
fun HabitList(
    habits: MutableList<Habit>
) {

    LazyColumn {

        itemsIndexed(habits) { index, habit ->

            HabitItem(
                habit = habit,
                onCompletedClick = {
                    habit.completed = !habit.completed
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
//composable to display single habit row
@Composable
fun HabitItem(
    habit: Habit,
    onCompletedClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = habit.name,
            color = if (habit.completed) Color.Gray else Color.Black,
            textDecoration = if (habit.completed)
                TextDecoration.LineThrough
            else
                TextDecoration.None
        )

        Button(onClick = onCompletedClick) {
            Text("Completed")
        }
    }
}