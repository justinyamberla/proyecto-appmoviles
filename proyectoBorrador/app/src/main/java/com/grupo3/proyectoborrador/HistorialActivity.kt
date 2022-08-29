package com.grupo3.proyectoborrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

private lateinit var btnLibro : ImageButton

class HistorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        btnLibro=findViewById(R.id.btnLibro)

        btnLibro.setOnClickListener{
            val intent = Intent(this, LibroActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }
}