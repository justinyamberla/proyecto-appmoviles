package com.grupo3.proyectoFinalBiblioTek.adicionales


data class Entrada (var fecha:String, var titulo:String, var autor:String, var isbn:String){
    constructor():this("","","","")
}