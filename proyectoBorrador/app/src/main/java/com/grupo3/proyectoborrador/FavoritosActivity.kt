package com.grupo3.proyectoborrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.proyectoborrador.adicionales.Libro


class FavoritosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        var libros = arrayListOf<Libro>()
        libros.add(Libro("TituloE","AutorE","IsbnE"))
        libros.add(Libro("TituloF","AutorF","IsbnF"))
        libros.add(Libro("TituloG","AutorG","IsbnG"))
        libros.add(Libro("TituloH","AutorH","IsbnH"))

        val recyclerViewConsulta: RecyclerView = findViewById(R.id.rvFavoritos);
        recyclerViewConsulta.layoutManager = LinearLayoutManager(this);
        recyclerViewConsulta.adapter = FavoritosAdapter(this,libros);
        recyclerViewConsulta.setHasFixedSize(true);
    }
}