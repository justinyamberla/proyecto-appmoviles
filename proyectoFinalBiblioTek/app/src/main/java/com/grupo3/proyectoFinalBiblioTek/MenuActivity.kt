package com.grupo3.proyectoFinalBiblioTek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

private lateinit var btnConsultar: ImageView
private lateinit var btnHistorial: ImageView
private lateinit var btnFavoritos: ImageView
private lateinit var btnSalir: Button

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnConsultar = findViewById(R.id.imageViewConsultar)
        btnHistorial = findViewById(R.id.imageViewHistorial)
        btnFavoritos = findViewById(R.id.imageViewFavoritos)
        btnSalir = findViewById(R.id.btnSalir)

        val email = intent.getStringExtra(EXTRA_LOGIN)


        btnConsultar.setOnClickListener {
            val intent = Intent(this, ConsultaActivity::class.java).apply {
            }
            intent.putExtra(EXTRA_LOGIN, email)
            startActivity(intent)
        }

        btnHistorial.setOnClickListener {
            val intent = Intent(this, HistorialActivity::class.java).apply {
            }
            intent.putExtra(EXTRA_LOGIN, email)
            startActivity(intent)
        }

        btnFavoritos.setOnClickListener {
            val intent = Intent(this, FavoritosActivity::class.java).apply {
            }
            intent.putExtra(EXTRA_LOGIN, email)
            startActivity(intent)
        }

        btnSalir.setOnClickListener {
            finish()
        }
    }


}