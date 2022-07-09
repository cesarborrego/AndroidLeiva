package com.example.exam

fun test() {

    //declarar argumentos del lado izquierdo
    val sum: (Int, Int) -> Int = { x, y -> x + y }

    //declarar argumentos del lado derecho
    val mul = { x: Int, y: Int -> x * y }

    //llamar a la función que incluye una lambda por parámetro
    val res = doOp(2, 3) { x, y ->
        x - y
    }
}

/**
 * Función que dados dos enteros regresará el resultado de la lambda
 */
fun doOp(x: Int, y: Int, op: (Int, Int) -> Int) = op(x, y)