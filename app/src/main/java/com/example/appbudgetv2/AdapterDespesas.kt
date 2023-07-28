package com.example.appbudgetv2

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView



class AdapterDespesas(val fragment: ListaDespesasFragment)  : RecyclerView.Adapter<AdapterDespesas.ViewHolderDespesa>()  {
    var cursor: Cursor? = null

        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class ViewHolderDespesa(contentor: View) : RecyclerView.ViewHolder(contentor) {
        private val textViewNome = contentor.findViewById<TextView>(R.id.textViewNaoSei)
        private val textViewTipo = contentor.findViewById<TextView>(R.id.textViewTipo)
        private val textViewValorAcumulado = contentor.findViewById<TextView>(R.id.textViewValorAcumulado)
        private val textViewValorTotal = contentor.findViewById<TextView>(R.id.textViewValorTotal)
        var botaoClick = contentor.findViewById<Button>(R.id.buttonDespesa)

        init {
            botaoClick.setOnClickListener {
                viewHolderSeleccionado?.desSeleciona()
                seleciona()
            }
        }

        private var viewHolderSeleccionado : ViewHolderDespesa? = null


        internal var despesa: Despesa? = null
        set(value) {
            field = value
            textViewNome.text = despesa?.nomeDespesa?: ""
            textViewTipo.text = despesa?.tipo?: ""
            textViewValorAcumulado.text = despesa?.valorAcumulado.toString()?: ""
            textViewValorTotal.text = despesa?.valorTotal.toString() ?: ""
        }

        fun seleciona() {
            viewHolderSeleccionado = this
            fragment.despesaSelecionada = despesa
            //itemView.setBackgroundResource(R.color.item_selecionado)
        }

        fun desSeleciona() {
            //itemView.setBackgroundResource(android.R.color.white)
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