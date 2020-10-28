package com.lucasdonato.marcadordetruco.extensions

import android.app.Activity
import android.widget.EditText
import android.widget.Toast

fun EditText.get() = text.toString().trim()

fun Activity.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()


