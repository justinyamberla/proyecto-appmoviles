package com.grupo3.proyectoborrador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.proyectoborrador.adicionales.Entrada
import com.grupo3.proyectoborrador.adicionales.Locacion

private lateinit var textViewTituloLibro:TextView
private lateinit var textViewAutorLibro:TextView
private lateinit var textViewIsbnLibro:TextView
class LibroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro)

        var locaciones = arrayListOf<Locacion>()
        locaciones.add(Locacion("Biblioteca de Ciencias","12","5to"))
        locaciones.add(Locacion("Biblioteca de Electrica","10","3ro"))
        locaciones.add(Locacion("Biblioteca de Quimica","10","5to"))


        textViewTituloLibro=findViewById(R.id.textViewTituloLibro)
        textViewAutorLibro=findViewById(R.id.textViewAutorLibro)
        textViewIsbnLibro=findViewById(R.id.textViewIsbnLibro)

        val titulo = intent.getStringExtra("titulo")
        val autor = intent.getStringExtra("autor")
        val isbn = intent.getStringExtra("isbn")

        textViewTituloLibro.text=titulo
        textViewAutorLibro.text=autor
        textViewIsbnLibro.text=isbn

        val recyclerViewConsulta: RecyclerView = findViewById(R.id.rvLocaciones);
        recyclerViewConsulta.layoutManager = LinearLayoutManager(this);
        recyclerViewConsulta.adapter = LibroAdapter(this,locaciones);
        recyclerViewConsulta.setHasFixedSize(true);



    }
}