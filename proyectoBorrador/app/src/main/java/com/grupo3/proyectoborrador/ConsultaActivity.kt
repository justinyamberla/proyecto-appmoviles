package com.grupo3.proyectoborrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.proyectoborrador.adicionales.Libro


class ConsultaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta)

        var libros = arrayListOf<Libro>()
        libros.add(Libro("a","a","a"))
        libros.add(Libro("b","a","a"))
        libros.add(Libro("c","a","a"))
        libros.add(Libro("d","a","a"))

        val recyclerViewConsulta: RecyclerView = findViewById(R.id.rvConsulta);
        recyclerViewConsulta.layoutManager = LinearLayoutManager(this);
        recyclerViewConsulta.adapter = ConsultaAdapter(libros);
        recyclerViewConsulta.setHasFixedSize(true);


    }
}