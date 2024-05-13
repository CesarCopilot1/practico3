package com.example.juegoapp.components

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.view.MotionEvent
import com.example.juegoapp.R
import com.example.juegoapp.model.Tablero

class TableroView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var model: Tablero = Tablero()
    private val joya1Img: Bitmap
    private val joya2Img: Bitmap
    private val joya3Img: Bitmap
    private val joya4Img: Bitmap
    private val joya5Img: Bitmap
    private val joya6Img: Bitmap
    private val imgNulo: Bitmap

    private val joya1Fuego: Bitmap
    private val joya2Fuego: Bitmap
    private val joya3Fuego: Bitmap
    private val joya4Fuego: Bitmap
    private val joya5Fuego: Bitmap
    private val joya6Fuego: Bitmap

    private val joya1Rayo: Bitmap
    private val joya2Rayo: Bitmap
    private val joya3Rayo: Bitmap
    private val joya4Rayo: Bitmap
    private val joya5Rayo: Bitmap
    private val joya6Rayo: Bitmap


    private var selectedRow = -1
    private var selectedColumn = -1

    private val tableroManager: TableroManager

    init {
        joya1Img = BitmapFactory.decodeResource(context?.resources, R.drawable.d1)
        joya2Img = BitmapFactory.decodeResource(context?.resources, R.drawable.d2)
        joya3Img = BitmapFactory.decodeResource(context?.resources, R.drawable.d3)
        joya4Img = BitmapFactory.decodeResource(context?.resources, R.drawable.d4)
        joya5Img = BitmapFactory.decodeResource(context?.resources, R.drawable.d5)
        joya6Img = BitmapFactory.decodeResource(context?.resources, R.drawable.d6)
        imgNulo = BitmapFactory.decodeResource(context?.resources, R.drawable.mario)

        joya1Fuego = BitmapFactory.decodeResource(context?.resources, R.drawable.d1fuego)
        joya2Fuego = BitmapFactory.decodeResource(context?.resources, R.drawable.d2fuego)
        joya3Fuego = BitmapFactory.decodeResource(context?.resources, R.drawable.d3fuego)
        joya4Fuego = BitmapFactory.decodeResource(context?.resources, R.drawable.d4fuego)
        joya5Fuego = BitmapFactory.decodeResource(context?.resources, R.drawable.d5fuego)
        joya6Fuego = BitmapFactory.decodeResource(context?.resources, R.drawable.d6fuego)

        joya1Rayo = BitmapFactory.decodeResource(context?.resources, R.drawable.d1rayo)
        joya2Rayo = BitmapFactory.decodeResource(context?.resources, R.drawable.d2rayo)
        joya3Rayo = BitmapFactory.decodeResource(context?.resources, R.drawable.d3rayo)
        joya4Rayo = BitmapFactory.decodeResource(context?.resources, R.drawable.d4rayo)
        joya5Rayo = BitmapFactory.decodeResource(context?.resources, R.drawable.d5rayo)
        joya6Rayo = BitmapFactory.decodeResource(context?.resources, R.drawable.d6rayo)

        tableroManager = TableroManager(model)
        tableroManager.eliminacionAutomatica()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val touchX = event.x.toInt()
                val touchY = event.y.toInt()
                val row = (touchY / (height / model.filas))
                val column = (touchX / (width / model.columnas))

                if (selectedRow == -1 && selectedColumn == -1) {
                    // Primera celda seleccionada
                    selectedRow = row
                    selectedColumn = column
                } else {
                    // Segunda celda seleccionada
                    if (tableroManager.isAdjacent(selectedRow, selectedColumn, row, column)) {
                        // Intercambio de joyas
                        val temp = model.matriz[selectedRow][selectedColumn]
                        model.matriz[selectedRow][selectedColumn] = model.matriz[row][column]
                        model.matriz[row][column] = temp

                        // Verificar si el intercambio forma una combinación de 3 joyas
                        if (!tableroManager.hayCombinacion(selectedRow, selectedColumn) &&
                            !tableroManager.hayCombinacion(row, column)
                        ) {
                            // Si no hay combinación, deshacer el intercambio
                            val temp2 = model.matriz[selectedRow][selectedColumn]
                            model.matriz[selectedRow][selectedColumn] = model.matriz[row][column]
                            model.matriz[row][column] = temp2
                        } else {
                            // Si hay combinación, eliminar las joyas y generar nuevas
                            tableroManager.eliminarCombinaciones()

                            invalidate()
                        }


                        selectedRow = -1
                        selectedColumn = -1
                    } else {

                        selectedRow = -1
                        selectedColumn = -1
                    }
                }

                invalidate()
                tableroManager.eliminacionAutomatica()
                return true
            }
        }
        return super.onTouchEvent(event)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val board = model
        val ancho = width / board.columnas
        val alto = height / board.filas
        for (i in 0 until board.filas) {
            for (j in 0 until board.columnas) {
                val bitmap = when (board.matriz[i][j]) {
                    1 -> joya1Img
                    2 -> joya2Img
                    3 -> joya3Img
                    4 -> joya4Img
                    5 -> joya5Img
                    6 -> joya6Img
                    7 -> joya1Fuego
                    8 -> joya2Fuego
                    9 -> joya3Fuego
                    10 -> joya4Fuego
                    11 -> joya5Fuego
                    12 -> joya6Fuego
                    13 -> joya1Rayo
                    14 -> joya2Rayo
                    15 -> joya3Rayo
                    16 -> joya4Rayo
                    17 -> joya5Rayo
                    18 -> joya6Rayo

                    else -> imgNulo // imagen nula
                }
                canvas.drawBitmap(
                    bitmap,
                    null,
                    Rect(j * ancho, i * alto, (j + 1) * ancho, (i + 1) * alto),
                    null
                )
            }
        }


    }

}
