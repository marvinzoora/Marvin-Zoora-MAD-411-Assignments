package com.example.habit_tracker_marvin_zoora

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//i use a data class for a habit and boolean whether it's completed
data class Habit(
    val id: Int,
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
    var habitText by rememberSaveable { mutableStateOf("") }
    var nextId by rememberSaveable { mutableStateOf(1) }

    // list to store habits
    val habitList = remember {
        mutableStateListOf<Habit>()
    }

    val context = LocalContext.current

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
            //update habitText when user enters new habit
            onTextChange = { habitText = it },
            onAddClick = {
                if (habitText.isNotBlank()) {
                    habitList.add(
                        Habit(
                            id = nextId,
                            name = habitText
                        )
                    )
                    nextId++
                    habitText = ""
                    Toast.makeText(context, "Habit added successfully", Toast.LENGTH_SHORT).show()
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        //display list of habits
        HabitList(
            habits = habitList,
            onCompletedClick = { index ->
                val oldHabit = habitList[index]
                habitList[index] = Habit(
                    id = oldHabit.id,
                    name = oldHabit.name,
                    completed = !oldHabit.completed
                )
            },
            onDeleteClick = { index ->
                habitList.removeAt(index)
            }
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

        //text field for habit
        OutlinedTextField(
            value = habitText,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter a new habit") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        //button to add habit
        FloatingActionButton(onClick = onAddClick) {
            Text("")
        }
    }
}

//dispaly list of habits
@Composable
fun HabitList(
    habits: MutableList<Habit>,
    onCompletedClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    //lazy column to display list
    LazyColumn {
        itemsIndexed(habits) { index, habit ->
            //display habits
            HabitItem(
                habit = habit,
                onCompletedClick = {
                    onCompletedClick(index)
                },
                onDeleteClick = {
                    onDeleteClick(index)
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
    onCompletedClick: () -> Unit,
    onDeleteClick: () -> Unit
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

        Row {
            //mark habit completed
            Button(onClick = onCompletedClick) {
                Text("Completed")
            }

            Spacer(modifier = Modifier.width(8.dp))

            //delete habit
            Button(onClick = onDeleteClick) {
                Text("Delete")
            }
        }
    }
}