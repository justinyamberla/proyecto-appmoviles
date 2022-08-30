package com.grupo3.proyectoborrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.grupo3.proyectoborrador.adicionales.Entrada
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

        val email = intent.getStringExtra(EXTRA_LOGIN)

        consultarFavoritos(email!!)

    }

    fun consultarFavoritos(email:String) {
        val db = Firebase.firestore
        db.collection("favoritos")
            .whereEqualTo ("Email",  email)
            .get()
            .addOnSuccessListener { result ->
                Log.d(EXTRA_LOGIN, "Success getting documents")
                var favoritos = ArrayList<Libro>()

                for (document in result) {
                    val titulo=document.data["Titulo"].toString()
                    val autor=document.data["Autor"].toString()
                    val isbn=document.data["Isbn"].toString()

                    val libro = Libro(titulo,autor,isbn)

                    favoritos.add(libro)
                }

                //Poblar en RecyclerView información usando mi adaptador
                val recyclerViewConsulta: RecyclerView = findViewById(R.id.rvFavoritos);
                recyclerViewConsulta.layoutManager = LinearLayoutManager(this);
                recyclerViewConsulta.adapter = FavoritosAdapter(this,favoritos);
                recyclerViewConsulta.setHasFixedSize(true);

            }
            .addOnFailureListener { exception ->
                Log.w(EXTRA_LOGIN, "Error getting documents.", exception)
                Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_LONG)
                    .show()
            }
    }
}