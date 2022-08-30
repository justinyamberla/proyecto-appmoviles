package com.grupo3.proyectoborrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.proyectoborrador.adicionales.Entrada
import com.grupo3.proyectoborrador.adicionales.Libro

private lateinit var btnLibro : ImageButton

class HistorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        var historial = arrayListOf<Entrada>()
        historial.add(Entrada("01-01-01","TituloE","AutorE","IsbnE"))
        historial.add(Entrada("02-02-02","TituloF","AutorF","IsbnF"))
        historial.add(Entrada("03-03-03","TituloG","AutorG","IsbnG"))
        historial.add(Entrada("04-04-04","TituloH","AutorH","IsbnH"))

        val recyclerViewConsulta: RecyclerView = findViewById(R.id.rvHistorial);
        recyclerViewConsulta.layoutManager = LinearLayoutManager(this);
        recyclerViewConsulta.adapter = HistorialAdapter(this,historial);
        recyclerViewConsulta.setHasFixedSize(true);

    }
}