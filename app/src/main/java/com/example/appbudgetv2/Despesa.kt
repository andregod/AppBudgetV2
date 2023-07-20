package com.example.appbudgetv2

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Despesa (
    var nomeDespesa: String,
    var tipo: String,
    var valorAcumulado: Double,
    var valorTotal: Double,
    var id: Long=-1
) : Serializable {

    fun toContentValues(): ContentValues {
        val valores= ContentValues()

        valores.put(TabelaDespesas.CAMPO_NOME_DESPESA, nomeDespesa)
        valores.put(TabelaDespesas.CAMPO_TIPO, tipo)
        valores.put(TabelaDespesas.CAMPO_VALOR_ACUMULADO, valorAcumulado)
        valores.put(TabelaDespesas.CAMPO_VALOR_TOTAL, valorTotal)
        return  valores
    }
    companion object {
        fun fromCursor(cursor: Cursor) : Despesa {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNomeDespesa = cursor.getColumnIndex(TabelaDespesas.CAMPO_NOME_DESPESA)
            val posTipo = cursor.getColumnIndex(TabelaDespesas.CAMPO_TIPO)
            val posvalorAcumulado = cursor.getColumnIndex(TabelaDespesas.CAMPO_VALOR_ACUMULADO)
            val posvalorTotal = cursor.getColumnIndex(TabelaDespesas.CAMPO_VALOR_TOTAL)

            val id = cursor.getLong(posId)
            val nomeDespesa = cursor.getString(posNomeDespesa)
            val tipo = cursor.getString(posTipo)
            val valorAcumulado= cursor.getDouble(posvalorAcumulado)
            val valorTotal= cursor.getDouble(posvalorTotal)

            return Despesa(nomeDespesa,tipo,valorAcumulado,valorTotal, id)
        }
    }
}