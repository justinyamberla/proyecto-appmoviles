package com.grupo3.proyectoborrador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.grupo3.proyectoborrador.adicionales.Entrada
import com.grupo3.proyectoborrador.adicionales.Locacion

private lateinit var textViewTituloLibro:TextView
private lateinit var textViewAutorLibro:TextView
private lateinit var textViewIsbnLibro:TextView
class LibroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro)

        textViewTituloLibro=findViewById(R.id.textViewTituloLibro)
        textViewAutorLibro=findViewById(R.id.textViewAutorLibro)
        textViewIsbnLibro=findViewById(R.id.textViewIsbnLibro)

        val titulo = intent.getStringExtra("titulo")
        val autor = intent.getStringExtra("autor")
        val isbn = intent.getStringExtra("isbn")

        textViewTituloLibro.text=titulo
        textViewAutorLibro.text=autor
        textViewIsbnLibro.text=isbn

        consultarLocaciones(isbn!!)
    }

    fun consultarLocaciones(isbn:String) {
        val db = Firebase.firestore
        db.collection("facultades")
            .whereArrayContains("Libros",  isbn)
            .get()
            .addOnSuccessListener { result ->
                Log.d(EXTRA_LOGIN, "Success getting documents")
                var locaciones = ArrayList<Locacion>()
                for (document in result) {
                    val nombre=document.data["Nombre"].toString()
                    val edificio=document.data["Edificio"].toString()
                    val piso=document.data["Piso"].toString()

                    val locacion = Locacion(nombre,edificio,piso)

                    locaciones.add(locacion)
                }

                //Poblar en RecyclerView información usando mi adaptador
                val recyclerViewConsulta: RecyclerView = findViewById(R.id.rvLocaciones);
                recyclerViewConsulta.layoutManager = LinearLayoutManager(this);
                recyclerViewConsulta.adapter = LibroAdapter(this,locaciones);
                recyclerViewConsulta.setHasFixedSize(true);

            }
            .addOnFailureListener { exception ->
                Log.w(EXTRA_LOGIN, "Error getting documents.", exception)
                Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_LONG)
                    .show()
            }
    }

}