package com.example.appbudgetv2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val VERSAO_BASE_DE_DADOS = 1
class BudgetOpenHelper (
    context: Context?
) : SQLiteOpenHelper(context, NOME_BASE_DE_DADOS, null, VERSAO_BASE_DE_DADOS) {
    override fun onCreate(db: SQLiteDatabase?) {
        requireNotNull(db)
        TabelaItens(db).cria()
        TabelaDespesas(db).cria()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        val NOME_BASE_DE_DADOS = "AppBudget.db"
    }
}