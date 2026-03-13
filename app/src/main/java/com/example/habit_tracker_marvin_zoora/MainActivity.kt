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
        //main ui
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
    //update habitText when user enters new habit
    onTextChange: (String) -> Unit,
    //
    onAddClick: () -> Unit
) {

    Column {

        Text("Enter a new habit")

        Spacer(modifier = Modifier.height(8.dp))

        //text field for habit
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
    //lazy column to display list
    LazyColumn {
        itemsIndexed(habits) { index, habit ->
            //display habits
            HabitItem(
                habit = habit,
                onCompletedClick = {
                    val oldHabit = habits[index]
                    habits[index] = Habit(oldHabit.name, !oldHabit.completed)
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

    //row layout for habits and button
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        //display habit
        //line to show completed
        Text(
            text = habit.name,
            color = if (habit.completed) Color.Gray else Color.Black,
            textDecoration = if (habit.completed)
                TextDecoration.LineThrough
            else
                TextDecoration.None
        )
        //mark habit completed
        Button(onClick = onCompletedClick) {
            Text("Completed")
        }
    }
}