package com.example.juegoapp.model

import kotlin.random.Random

class Tablero {
    var filas = 8
    var columnas = 8

    // matriz llena de números del 1 al 6
    var matriz: Array<Array<Int>> = Array(filas) {
        Array(columnas) {
            Random.nextInt(1, 7)
        }
    }

}