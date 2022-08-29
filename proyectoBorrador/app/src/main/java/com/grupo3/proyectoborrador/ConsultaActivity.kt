package com.grupo3.proyectoborrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.grupo3.proyectoborrador.adicionales.Libro

private lateinit var libros:ArrayList<Libro>
private lateinit var imageButtonBuscar: ImageButton
private lateinit var spinnerCategoria:Spinner
private lateinit var editTextCampo:EditText

class ConsultaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta)

        imageButtonBuscar=findViewById(R.id.imageButtonBuscar)
        spinnerCategoria=findViewById(R.id.spinnerCategoria)
        editTextCampo=findViewById(R.id.editTextCampo)

        libros=consultarLibros()


        imageButtonBuscar.setOnClickListener{
            var resultado = ArrayList<Libro>()
            for (libro in libros){
                if(spinnerCategoria.selectedItem=="Autor"){
                    if (libro.autor.contains(editTextCampo.text)){
                        resultado.add(libro)
                    }

                }
                if(spinnerCategoria.selectedItem=="ISBN"){
                    if (libro.isbn.contains(editTextCampo.text)){
                        resultado.add(libro)
                    }
                }
                if(spinnerCategoria.selectedItem=="Título"){
                    if (libro.titulo.contains(editTextCampo.text)){
                        resultado.add(libro)
                    }
                }
            }

            val recyclerViewConsulta: RecyclerView = findViewById(R.id.rvConsulta);
            recyclerViewConsulta.layoutManager = LinearLayoutManager(this);
            recyclerViewConsulta.adapter = ConsultaAdapter(this,resultado);
            recyclerViewConsulta.setHasFixedSize(true);
        }


    }

    fun consultarLibros():ArrayList<Libro> {
        var libros = ArrayList<Libro>()
        val db = Firebase.firestore
        db.collection("libros")
            .get()
            .addOnSuccessListener { result ->
                Log.d(EXTRA_LOGIN, "Success getting documents")

                for (document in result) {
                    val titulo=document.data["Titulo"].toString()
                    val autor=document.data["Autor"].toString()
                    val isbn=document.data["Isbn"].toString()

                    val libro = Libro(titulo,autor,isbn)

                    libros.add(libro)
                }

                val recyclerViewConsulta: RecyclerView = findViewById(R.id.rvConsulta);
                recyclerViewConsulta.layoutManager = LinearLayoutManager(this);
                recyclerViewConsulta.adapter = ConsultaAdapter(this,libros);
                recyclerViewConsulta.setHasFixedSize(true);

            }
            .addOnFailureListener { exception ->
                Log.w(EXTRA_LOGIN, "Error getting documents.", exception)
                Toast.makeText(this, "Error al obtener datos.", Toast.LENGTH_LONG)
                    .show()
            }
        return libros
    }
}