package com.example.appbudgetv2

class BudgetOpenHelper (
    context: Context?
) : SQLiteOpenHelper(context, NOME_BASE_DE_DADOS, null, VERSAO_BASE_DE_DADOS) {
    override fun onCreate(db: SQLiteDatabase?) {
        requireNotNull(db)
        TabelaItens(db!!).cria()
        TabelaDespesas(db!!).cria()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        val NOME_BASE_DE_DADOS = "AppBudget.db"
    }
}