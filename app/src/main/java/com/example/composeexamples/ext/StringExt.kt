package com.example.composeexamples.ext

fun String.sizeWithoutSpace(): Int = this.replace(" ", "").count()
