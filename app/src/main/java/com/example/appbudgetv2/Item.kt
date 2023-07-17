package com.example.appbudgetv2

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable
import java.util.Calendar

data class Item (

    var nome:String,
    var custo:Double,
    var data: Calendar? = null,
    var despesa: Despesa,
    var id: Long = -1
): Serializable {

    fun toContentValues(): ContentValues {
        val valores= ContentValues()

        valores.put(TabelaItens.CAMPO_NOME, nome)
        valores.put(TabelaItens.CAMPO_CUSTO, custo)
        valores.put(TabelaItens.CAMPO_DATA, data?.timeInMillis)
        valores.put(TabelaItens.CAMPO_FK_DESPESA,despesa.id)
        return  valores
    }
    companion object {
        fun fromCursor(cursor: Cursor): Item{

            val posId=cursor.getColumnIndex(BaseColumns._ID)
            val posNomeItem=cursor.getColumnIndex(TabelaItens.CAMPO_NOME)
            val posData=cursor.getColumnIndex(TabelaItens.CAMPO_DATA)
            val posCusto=cursor.getColumnIndex(TabelaItens.CAMPO_CUSTO)



            val posDespesaFK=cursor.getColumnIndex(TabelaItens.CAMPO_FK_DESPESA)
            val posDespesaNome=cursor.getColumnIndex(TabelaItens.CAMPO_NOME_DESPESA)
            val posDespesaTipo=cursor.getColumnIndex(TabelaItens.CAMPO_TIPO)
            val posDespesaValorAcumulado=cursor.getColumnIndex(TabelaItens.CAMPO_VALOR_ACUMULADO)
            val posDespesaValorTotal=cursor.getColumnIndex(TabelaItens.CAMPO_VALOR_TOTAL)


            val id= cursor.getLong(posId)
            val nomeItem=cursor.getString(posNomeItem)
            val custo=cursor.getDouble(posCusto)

            var data: Calendar?

            if (cursor.isNull(posData)) {
                data = null
            } else {
                data = Calendar.getInstance()
                data.timeInMillis = cursor.getLong((posData))
            }

            val idDespesa=cursor.getLong(posDespesaFK)
            val DespesaNome=cursor.getString(posDespesaNome)
            val DespesaTipo=cursor.getString(posDespesaTipo)
            val DespesaValorAcumulado=cursor.getDouble(posDespesaValorAcumulado)
            val DespesaValorTotal=cursor.getDouble(posDespesaValorTotal)

            return Item(nomeItem,custo, data!!,Despesa(DespesaNome,DespesaTipo,DespesaValorAcumulado,DespesaValorTotal,idDespesa),id)
        }
    }
}