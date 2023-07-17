package com.example.appbudgetv2

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaDespesas(db: SQLiteDatabase):TabelaBD(db,"despesa") {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA,$CAMPO_NOME_DESPESA TEXT NOT NULL,$CAMPO_TIPO TEXT NOT NULL,$CAMPO_VALOR_ACUMULADO DOUBLE NOT NULL,$CAMPO_VALOR_TOTAL DOUBLE NOT NULL )")
    }

    companion object {
        const val NOME_TABELA = "despesa"
        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME_DESPESA = "nome_despesa"
        const val CAMPO_TIPO = "tipo"
        const val CAMPO_VALOR_ACUMULADO = "valorAcumulado"
        const val CAMPO_VALOR_TOTAL = "valorTotal"

        //const val CHAVE_TABELA_DESPESAS = "${NOME_TABELA}.${BaseColumns._ID}"

        val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_NOME_DESPESA, CAMPO_TIPO,CAMPO_VALOR_ACUMULADO,CAMPO_VALOR_TOTAL)


    }
}