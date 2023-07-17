package com.example.appbudgetv2

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaItens(db: SQLiteDatabase): TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA,$CAMPO_NOME TEXT NOT NULL,$CAMPO_CUSTO DOUBLE NOT NULL,$CAMPO_DATA INTEGER NOT NULL,$CAMPO_FK_DESPESA INTEGER NOT NULL,FOREIGN KEY($CAMPO_FK_DESPESA) REFERENCES ${TabelaDespesas.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    override  fun  consulta(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        groupby: String?,
        having: String?,
        orderby: String?
    ): Cursor {
        val sql= SQLiteQueryBuilder()
        sql.tables="$NOME_TABELA INNER JOIN ${TabelaDespesas.NOME_TABELA} ON ${TabelaDespesas.CAMPO_ID}= $CAMPO_FK_DESPESA"
        return sql.query(db, colunas, selecao, argsSelecao, groupby, having, orderby)
    }
    companion object{
        const val NOME_TABELA = "item"
        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME = "nome"
        const val CAMPO_CUSTO = "custo"
        const val CAMPO_DATA= "data"

        const val CAMPO_FK_DESPESA = "id_despesa"
        const val CAMPO_NOME_DESPESA = TabelaDespesas.CAMPO_NOME_DESPESA
        const val CAMPO_TIPO = TabelaDespesas.CAMPO_TIPO
        const val CAMPO_VALOR_ACUMULADO = TabelaDespesas.CAMPO_VALOR_ACUMULADO
        const val CAMPO_VALOR_TOTAL = TabelaDespesas.CAMPO_VALOR_TOTAL


        val CAMPOS= arrayOf(
            CAMPO_ID,
            CAMPO_NOME,
            CAMPO_CUSTO,
            CAMPO_DATA,
            CAMPO_FK_DESPESA,
            CAMPO_NOME_DESPESA,
            CAMPO_TIPO,
            CAMPO_VALOR_ACUMULADO,
            CAMPO_VALOR_TOTAL
        )

    }
}