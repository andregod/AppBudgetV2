package com.example.appbudgetv2

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterDespesas(val fragment: ListaDespesasFragment)  : RecyclerView.Adapter<AdapterDespesas.ViewHolderDespesa>()  {
    var cursor: Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    inner class ViewHolderDespesa(contentor: View) : RecyclerView.ViewHolder(contentor) {
        private val textViewNome = contentor.findViewById<TextView>(R.id.textViewNomeDespesa)
        private val textViewTipo = contentor.findViewById<TextView>(R.id.textViewTipo)
        private val textViewValorAcumulado = contentor.findViewById<TextView>(R.id.textViewValorAcumulado)
        private val textViewValorTotal = contentor.findViewById<TextView>(R.id.textViewValorTotal)
    internal var despesa: Despesa? = null
        set(value) {
            field = value
            textViewNome.text = despesa?.nomeDespesa?: ""
            textViewTipo.text = despesa?.tipo?: ""
            textViewValorAcumulado.text = despesa?.valorAcumulado.toString()?: ""
            textViewValorTotal.text = despesa?.valorTotal.toString() ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDespesa {
        return ViewHolderDespesa(
            fragment.layoutInflater.inflate(R.layout.item_despesa, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolderDespesa, position: Int) {
        cursor!!.moveToPosition(position)
        holder.despesa = Despesa.fromCursor(cursor!!)
    }
}