package com.grupo3.proyectoFinalBiblioTek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.grupo3.proyectoFinalBiblioTek.adicionales.Libro


class FavoritosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        val email = intent.getStringExtra(EXTRA_LOGIN)

        consultarFavoritos(email!!)

    }

    fun consultarFavoritos(email: String) {
        val db = Firebase.firestore
        db.collection("favoritos")
            .whereEqualTo("Email", email)
            .get()
            .addOnSuccessListener { result ->
                Log.d(EXTRA_LOGIN, "Success getting documents")
                var favoritos = ArrayList<Libro>()

                for (document in result) {
                    val titulo = document.data["Titulo"].toString()
                    val autor = document.data["Autor"].toString()
                    val isbn = document.data["Isbn"].toString()

                    val libro = Libro(titulo, autor, isbn)

                    favoritos.add(libro)
                }

                //Poblar en RecyclerView información usando mi adaptador
                val recyclerViewConsulta: RecyclerView = findViewById(R.id.rvFavoritos);
                val layoutManager = LinearLayoutManager(this)
                recyclerViewConsulta.layoutManager = layoutManager
                recyclerViewConsulta.adapter = FavoritosAdapter(this, favoritos, email);
                recyclerViewConsulta.setHasFixedSize(true);
                recyclerViewConsulta.addItemDecoration(
                    DividerItemDecoration(
                        baseContext,
                        layoutManager.orientation
                    )
                )

            }
            .addOnFailureListener { exception ->
                Log.w(EXTRA_LOGIN, "Error getting documents.", exception)
                Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_LONG)
                    .show()
            }
    }
}