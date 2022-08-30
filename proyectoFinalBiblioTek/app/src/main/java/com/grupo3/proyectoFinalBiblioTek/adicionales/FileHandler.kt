package com.grupo3.proyectoFinalBiblioTek.adicionales

interface FileHandler {
    fun SaveInformation(datosAGrabar:Pair<String,String>)
    fun ReadInformation():Pair<String,String>
}