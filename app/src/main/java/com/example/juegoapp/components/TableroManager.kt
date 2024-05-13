package com.example.juegoapp.components

import com.example.juegoapp.model.Tablero
import kotlin.random.Random

class TableroManager(private val tablero: Tablero) {

    fun eliminarCombinaciones() {
        var hayCombinaciones = false

        // Marcar las combinaciones a eliminar
        val combinaciones = Array(tablero.filas) { BooleanArray(tablero.columnas) }
        for (i in 0 until tablero.filas) {
            for (j in 0 until tablero.columnas) {
                if (hayCombinacion(i, j)) {
                    combinaciones[i][j] = true
                    hayCombinaciones = true
                }
            }
        }

        // Eliminar las joyas marcadas y desplazar las joyas superiores
        if (hayCombinaciones) {
            for (i in 0 until tablero.filas) {
                for (j in 0 until tablero.columnas) {
                    if (combinaciones[i][j]) {
                        for (k in i downTo 1) {
                            tablero.matriz[k][j] = tablero.matriz[k - 1][j]
                        }
                        if (hay5(i, j)) {
                            // Si hay una combinación de cuatro, reemplazar con joya especial "fuego"
                            val b= tablero.matriz[i][j]
                            tablero.matriz[0][j] = tablero.matriz[i][j]+12 // 7 representa la joya especial "fuego"
                        }else if (hay4(i, j)) {
                            // Si hay una combinación de cuatro, reemplazar con joya especial "fuego"
                            val b= tablero.matriz[i][j]
                                                                                                                                                                                                                    tablero.matriz[0][j] = tablero.matriz[i][j]+6 // 7 representa la joya especial "fuego"
                        } else {
                            // Si no, genera una joya aleatoria normal
                            tablero.matriz[0][j] = Random.nextInt(1, 7)
                        }
                    }
                }
            }
        }

    }

    fun hayCombinacion(row: Int, column: Int): Boolean {
        val joyaActual = tablero.matriz[row][column] % 6

        // Verifica horizontalmente
        var contador = 1
        var i = column - 1
        while (i >= 0 && tablero.matriz[row][i] %6 == joyaActual) {
            contador++
            i--
        }
        i = column + 1
        while (i < tablero.columnas && tablero.matriz[row][i] %6 == joyaActual) {
            contador++
            i++
        }
        if (contador >= 3) return true

        // Verifica verticalmente
        contador = 1
        var j = row - 1
        while (j >= 0 && tablero.matriz[j][column]%6 == joyaActual) {
            contador++
            j--
        }
        j = row + 1
        while (j < tablero.filas && tablero.matriz[j][column]%6 == joyaActual) {
            contador++
            j++
        }
        return contador >= 3
    }

    fun hay5(row: Int, column: Int): Boolean {
        val joyaActual = tablero.matriz[row][column]

        // Verifica horizontalmente
        var contador = 1
        var i = column - 1
        while (i >= 0 && tablero.matriz[row][i]  == joyaActual) {
            contador++
            i--
        }
        i = column + 1
        while (i < tablero.columnas && tablero.matriz[row][i]  == joyaActual) {
            contador++
            i++
        }
        if (contador >= 5) return true

        // Verifica verticalmente
        contador = 1
        var j = row - 1
        while (j >= 0 && tablero.matriz[j][column]  == joyaActual) {
            contador++
            j--
        }
        j = row + 1
        while (j < tablero.filas && tablero.matriz[j][column]  == joyaActual) {
            contador++
            j++
        }
        return contador >= 5
    }

    fun hay4(row: Int, column: Int): Boolean {
        val joyaActual = tablero.matriz[row][column]

        // Verifica horizontalmente
        var contador = 1
        var i = column - 1
        while (i >= 0 && tablero.matriz[row][i]  == joyaActual) {
            contador++
            i--
        }
        i = column + 1
        while (i < tablero.columnas && tablero.matriz[row][i]  == joyaActual) {
            contador++
            i++
        }
        if (contador == 4) return true

        // Verifica verticalmente
        contador = 1
        var j = row - 1
        while (j >= 0 && tablero.matriz[j][column]  == joyaActual) {
            contador++
            j--
        }
        j = row + 1
        while (j < tablero.filas && tablero.matriz[j][column] == joyaActual) {
            contador++
            j++
        }
        return contador == 4
    }


    fun isAdjacent(row1: Int, col1: Int, row2: Int, col2: Int): Boolean {
        if(row1 == row2){
            val resta =col1 - col2
            if (resta ==1 || resta ==-1) return true
        }
        if(col1 == col2){
            val resta =row1 - row2
            if (resta ==1 || resta ==-1) return true
        }
        return false
    }

    // Eliminación automática de combinaciones
    fun eliminacionAutomatica() {
        var hayCombinaciones = true
        while (hayCombinaciones) {
            hayCombinaciones = false

            // Marcar las combinaciones a eliminar
            val combinaciones = Array(tablero.filas) { BooleanArray(tablero.columnas) }
            for (i in 0 until tablero.filas) {
                for (j in 0 until tablero.columnas) {
                    if (hayCombinacion(i, j)) {
                        combinaciones[i][j] = true
                        hayCombinaciones = true
                    }
                }
            }

            // Eliminar las joyas marcadas y desplazar las joyas superiores
            if (hayCombinaciones) {
                for (i in 0 until tablero.filas) {
                    for (j in 0 until tablero.columnas) {
                        if (combinaciones[i][j]) {
                            for (k in i downTo 1) {
                                tablero.matriz[k][j] = tablero.matriz[k - 1][j]
                            }
                            if (hay5(i, j)) {
                                // Si hay una combinación de cuatro, reemplazar con joya especial "fuego"
                                val b= tablero.matriz[i][j]
                                tablero.matriz[0][j] = tablero.matriz[i][j]+12 // 7 representa la joya especial "fuego"
                            }else if (hay4(i, j)) {
                                // Si hay una combinación de cuatro, reemplazar con joya especial "fuego"
                                val b= tablero.matriz[i][j]
                                tablero.matriz[0][j] = tablero.matriz[i][j]+6 // 7 representa la joya especial "fuego"
                            } else {
                                // Si no, genera una joya aleatoria normal
                                tablero.matriz[0][j] = Random.nextInt(1, 7)
                            }
                        }
                    }
                }
            }
        }
    }
}
