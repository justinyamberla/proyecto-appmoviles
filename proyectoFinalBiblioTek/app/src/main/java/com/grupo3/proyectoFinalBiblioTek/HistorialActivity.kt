package com.grupo3.proyectoFinalBiblioTek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.grupo3.proyectoFinalBiblioTek.adicionales.Entrada

class HistorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        val email = intent.getStringExtra(EXTRA_LOGIN)

        consultarHistorial(email!!)
    }

    fun consultarHistorial(email:String) {
        val db = Firebase.firestore
        db.collection("historial")
            .whereEqualTo ("Email",  email)
            .orderBy("Fecha", Query.Direction.DESCENDING)
            .limit(10)
            .get()
            .addOnSuccessListener { result ->
                Log.d(EXTRA_LOGIN, "Success getting documents")
                var historial = ArrayList<Entrada>()

                for (document in result) {
                    val fecha=document.data["Fecha"].toString()
                    val titulo=document.data["Titulo"].toString()
                    val autor=document.data["Autor"].toString()
                    val isbn=document.data["Isbn"].toString()

                    val entrada = Entrada(fecha,titulo,autor,isbn)

                    historial.add(entrada)
                }

                //Poblar en RecyclerView información usando mi adaptador
                val recyclerViewConsulta: RecyclerView = findViewById(R.id.rvHistorial);
                val layoutManager = LinearLayoutManager(this)
                recyclerViewConsulta.layoutManager =layoutManager
                recyclerViewConsulta.adapter = HistorialAdapter(this,historial,email);
                recyclerViewConsulta.setHasFixedSize(true)
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