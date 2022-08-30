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
        libros.add(Libro("TituloAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA","AutorA","IsbnA"))
        libros.add(Libro("TituloB","AutorB","IsbnB"))
        libros.add(Libro("TituloC","AutorC","IsbnC"))
        libros.add(Libro("TituloD","AutorD","IsbnD"))

        val recyclerViewConsulta: RecyclerView = findViewById(R.id.rvConsulta);
        recyclerViewConsulta.layoutManager = LinearLayoutManager(this);
        recyclerViewConsulta.adapter = ConsultaAdapter(this,libros);
        recyclerViewConsulta.setHasFixedSize(true);



    }
}