package com.grupo3.proyectoborrador

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.proyectoborrador.adicionales.Locacion

class LibroAdapter(private val mContext: Context, private val dataSet:ArrayList<Locacion>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER: Int = 0

    class ViewHolderHeader(view: View) : RecyclerView.ViewHolder(view) {
        val textViewNombre: TextView = view.findViewById(R.id.textViewNombreL)
        val textViewEdificio: TextView = view.findViewById(R.id.textViewEdificioL)
        val textViewPiso: TextView = view.findViewById(R.id.textViewPisoL)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewNombre: TextView
        val textViewEdificio: TextView
        val textViewPiso: TextView

        init {
            textViewNombre = view.findViewById(R.id.textViewNombreL)
            textViewEdificio = view.findViewById(R.id.textViewEdificioL)
            textViewPiso = view.findViewById(R.id.textViewPisoL)
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
                LayoutInflater.from(parent.context).inflate(R.layout.locaciones_list, parent, false)
            return ViewHolderHeader(header)
        }
        val header =
            LayoutInflater.from(parent.context).inflate(R.layout.locaciones_list, parent, false)
        return ViewHolder(header)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderHeader) {
            holder.textViewNombre.setTypeface(null, Typeface.BOLD)
            holder.textViewEdificio.setTypeface(null, Typeface.BOLD)
            holder.textViewPiso.setTypeface(null, Typeface.BOLD)
            holder.textViewNombre.setTextColor((R.color.propio).toInt())
            holder.textViewEdificio.setTextColor((R.color.propio).toInt())
            holder.textViewPiso.setTextColor((R.color.propio).toInt())

        } else if (holder is ViewHolder) {
            holder.textViewNombre.text = dataSet[position - 1].nombre
            holder.textViewEdificio.text = dataSet[position - 1].edificio
            holder.textViewPiso.text = dataSet[position - 1].piso
        }
    }

    override fun getItemCount() = dataSet.size + 1
}