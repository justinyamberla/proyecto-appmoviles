package com.grupo3.proyectoFinalBiblioTek

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.grupo3.proyectoFinalBiblioTek.adicionales.Libro
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ConsultaAdapter(
    private val mContext: Context, private val dataSet: ArrayList<Libro>,
    private val email: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER: Int = 0

    class ViewHolderHeader(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitulo: TextView = view.findViewById(R.id.textViewTituloC)
        val textViewAutor: TextView = view.findViewById(R.id.textViewAutorC)
        val textViewIsbn: TextView = view.findViewById(R.id.textViewIsbnC)
        val textViewAcciones: TextView = view.findViewById(R.id.textViewAccionesC)
        val btnVer: ImageButton = view.findViewById(R.id.btnVerC)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitulo: TextView
        val textViewAutor: TextView
        val textViewIsbn: TextView
        val textViewAcciones: TextView
        val btnVer: ImageButton

        init {
            textViewTitulo = view.findViewById(R.id.textViewTituloC)
            textViewAutor = view.findViewById(R.id.textViewAutorC)
            textViewIsbn = view.findViewById(R.id.textViewIsbnC)
            textViewAcciones = view.findViewById(R.id.textViewAccionesC)
            btnVer = view.findViewById(R.id.btnVerC)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val header =
                LayoutInflater.from(parent.context).inflate(R.layout.consulta_list, parent, false)
            return ViewHolderHeader(header)
        }
        val header =
            LayoutInflater.from(parent.context).inflate(R.layout.consulta_list, parent, false)
        return ViewHolder(header)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderHeader) {
            holder.btnVer.visibility = View.INVISIBLE
            holder.textViewAcciones.visibility = View.VISIBLE
            holder.textViewTitulo.setTypeface(null, Typeface.BOLD)
            holder.textViewAutor.setTypeface(null, Typeface.BOLD)
            holder.textViewIsbn.setTypeface(null, Typeface.BOLD)
            holder.textViewAcciones.setTypeface(null, Typeface.BOLD)
            holder.textViewTitulo.setTextColor((R.color.propio).toInt())
            holder.textViewAutor.setTextColor((R.color.propio).toInt())
            holder.textViewIsbn.setTextColor((R.color.propio).toInt())
            holder.textViewAcciones.setTextColor((R.color.propio).toInt())

        } else if (holder is ViewHolder) {
            holder.btnVer.visibility = View.VISIBLE
            holder.textViewAcciones.visibility = View.INVISIBLE
            holder.textViewTitulo.text = dataSet[position - 1].titulo
            holder.textViewAutor.text = dataSet[position - 1].autor
            holder.textViewIsbn.text = dataSet[position - 1].isbn

            holder.btnVer.setOnClickListener {
                val titulo = dataSet[position - 1].titulo
                val autor = dataSet[position - 1].autor
                val isbn = dataSet[position - 1].isbn
                val intent = Intent(mContext, LibroActivity::class.java).apply {
                    putExtra("titulo", titulo)
                    putExtra("autor", autor)
                    putExtra("isbn", isbn)
                    putExtra(EXTRA_LOGIN, email)
                }
                ingresarHistorial(titulo, autor, isbn, email)
                mContext.startActivity(intent)

            }
        }
    }

    override fun getItemCount() = dataSet.size + 1

    fun ingresarHistorial(titulo: String, autor: String, isbn: String, email: String) {

        val db = Firebase.firestore

        val actual = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss")
        val fecha = actual.format(formato)


        val data = hashMapOf(
            "Fecha" to fecha,
            "Autor" to autor,
            "Titulo" to titulo,
            "Isbn" to isbn,
            "Email" to email
        )

        db.collection("historial")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(EXTRA_LOGIN, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(EXTRA_LOGIN, "Error adding document", e)
            }
    }
}