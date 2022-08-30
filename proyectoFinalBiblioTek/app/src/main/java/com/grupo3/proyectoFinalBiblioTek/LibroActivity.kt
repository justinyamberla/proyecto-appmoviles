package com.grupo3.proyectoFinalBiblioTek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.grupo3.proyectoFinalBiblioTek.adicionales.Locacion

private lateinit var textViewTituloLibro: TextView
private lateinit var textViewAutorLibro: TextView
private lateinit var textViewIsbnLibro: TextView
private lateinit var imageButtonFavoritosL: ImageButton

class LibroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro)

        textViewTituloLibro = findViewById(R.id.textViewTituloLibro)
        textViewAutorLibro = findViewById(R.id.textViewAutorLibro)
        textViewIsbnLibro = findViewById(R.id.textViewIsbnLibro)
        imageButtonFavoritosL = findViewById(R.id.imageButtonFavoritosL)

        val titulo = intent.getStringExtra("titulo")
        val autor = intent.getStringExtra("autor")
        val isbn = intent.getStringExtra("isbn")

        val email = intent.getStringExtra(EXTRA_LOGIN)

        textViewTituloLibro.text = titulo
        textViewAutorLibro.text = autor
        textViewIsbnLibro.text = isbn

        consultarLocaciones(isbn!!)

        imageButtonFavoritosL.setOnClickListener {
            val db = Firebase.firestore
            db.collection("favoritos")
                .whereEqualTo("Email", email)
                .get()
                .addOnSuccessListener { result ->
                    Log.d(EXTRA_LOGIN, "Success getting documents")
                    var repetido = 0
                    for (document in result) {
                        val isbnD = document.data["Isbn"].toString()
                        if (isbn == isbnD) {
                            Toast.makeText(
                                this,
                                "Este libro ya está en favoritos",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            repetido = 1
                        }
                    }

                    if (repetido != 1) {
                        val data = hashMapOf(
                            "Autor" to autor,
                            "Titulo" to titulo,
                            "Isbn" to isbn,
                            "Email" to email
                        )
                        db.collection("favoritos")
                            .add(data)
                            .addOnSuccessListener { documentReference ->
                                Log.d(
                                    EXTRA_LOGIN,
                                    "DocumentSnapshot written with ID: ${documentReference.id}"
                                )
                                Toast.makeText(
                                    this,
                                    "Favorito agregado exitosamente",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            .addOnFailureListener { e ->
                                Log.w(EXTRA_LOGIN, "Error adding document", e)
                            }
                    }


                }
                .addOnFailureListener { exception ->
                    Log.w(EXTRA_LOGIN, "Error getting documents.", exception)
                    Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_LONG)
                        .show()
                }
        }
    }

    fun consultarLocaciones(isbn: String) {
        val db = Firebase.firestore
        db.collection("facultades")
            .whereArrayContains("Libros", isbn)
            .get()
            .addOnSuccessListener { result ->
                Log.d(EXTRA_LOGIN, "Success getting documents")
                var locaciones = ArrayList<Locacion>()
                for (document in result) {
                    val nombre = document.data["Nombre"].toString()
                    val edificio = document.data["Edificio"].toString()
                    val piso = document.data["Piso"].toString()

                    val locacion = Locacion(nombre, edificio, piso)

                    locaciones.add(locacion)
                }

                //Poblar en RecyclerView información usando mi adaptador
                val recyclerViewConsulta: RecyclerView = findViewById(R.id.rvLocaciones);
                recyclerViewConsulta.layoutManager = LinearLayoutManager(this);
                recyclerViewConsulta.adapter = LibroAdapter(this, locaciones);
                recyclerViewConsulta.setHasFixedSize(true);

            }
            .addOnFailureListener { exception ->
                Log.w(EXTRA_LOGIN, "Error getting documents.", exception)
                Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_LONG)
                    .show()
            }
    }

    fun agregarFavorito(email: String, isbn: String) {

    }

}