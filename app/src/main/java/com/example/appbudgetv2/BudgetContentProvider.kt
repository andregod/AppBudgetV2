package com.example.appbudgetv2

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class BudgetContentProvider: ContentProvider() {
    private var bdopenHelper: BudgetOpenHelper?=null
    override fun onCreate(): Boolean {
        bdopenHelper= BudgetOpenHelper(context)
        return true
    }

    override fun query(
        uri:Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd=bdopenHelper!!.readableDatabase
        val endereco=uriMatcher().match(uri)

        val tabela=when(endereco) {
            URI_DESPESAS, URI_DESPESAS_ID -> TabelaDespesas(bd)
            URI_ITENS,URI_ITENS_ID -> TabelaItens(bd)
            else -> null
        }
        val id=uri.lastPathSegment
        val (selecao,argsSel) =when(endereco) {
            URI_ITENS_ID,URI_DESPESAS_ID -> Pair("${ BaseColumns._ID}=?", arrayOf(id))
            else -> Pair(selection,selectionArgs)
        }
        return tabela?.consulta(
            projection as Array<String>,
            selecao,
            argsSel as Array<String>?,
            null,
            null,
            sortOrder)
    }

    override fun getType(uri: Uri): String? {
        val endereco=uriMatcher().match(uri)

        return when(endereco) {
            URI_ITENS -> "vnd.android.cursor.dir/$ITENS"
            URI_ITENS_ID -> "vnd.android.cursor.item/$ITENS"
            URI_DESPESAS -> "vnd.android.cursor.dir/$DESPESAS"
            URI_DESPESAS_ID -> "vnd.android.cursor.item/$DESPESAS"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd=bdopenHelper!!.writableDatabase

        val endereco=uriMatcher().match(uri)

        val tabela=when(endereco) {
            URI_DESPESAS -> TabelaDespesas(bd)
            URI_ITENS -> TabelaItens(bd)
            else -> return null
        }

        val id=tabela.insere(values!!)
        if (id==-1L){
            return null
        }
        return Uri.withAppendedPath(uri,id.toString())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd=bdopenHelper!!.writableDatabase
        val endereco=uriMatcher().match(uri)
        val tabela=when(endereco) {
            URI_DESPESAS_ID -> TabelaDespesas(bd)
            URI_ITENS_ID -> TabelaItens(bd)
            else -> return 0 //zero linhas afetadas
        }
        val id= uri.lastPathSegment!!
        return tabela.elimine("${ BaseColumns._ID}=?", arrayOf(id))
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd=bdopenHelper!!.writableDatabase
        val endereco=uriMatcher().match(uri)
        val tabela=when(endereco) {
            URI_DESPESAS_ID -> TabelaDespesas(bd)
            URI_ITENS_ID -> TabelaItens(bd)
            else -> return 0 //zero linhas afetadas
        }
        val id= uri.lastPathSegment!!
        return tabela.altera(values!!,"${ BaseColumns._ID}=?", arrayOf(id))
    }


    companion object{

        private const val AUTORIDADE="pt.ipg.sapatilhas"

        private const val DESPESAS="despesas"
        private const val ITENS="itens"

        private const val URI_DESPESAS=100
        private const val URI_DESPESAS_ID=101
        private const val URI_ITENS=200
        private const val URI_ITENS_ID=201

        private val ENDERECO_BASE=Uri.parse("content://$AUTORIDADE")
        val ENDERECO_ITENS=Uri.withAppendedPath(ENDERECO_BASE,ITENS)
        val ENDERECO_DESPESA=Uri.withAppendedPath(ENDERECO_BASE,DESPESAS)
        fun  uriMatcher()= UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE,DESPESAS,URI_DESPESAS)
            addURI(AUTORIDADE,"$DESPESAS/#",URI_DESPESAS_ID)
            addURI(AUTORIDADE,ITENS,URI_ITENS)
            addURI(AUTORIDADE,"$ITENS/#",URI_ITENS_ID)

        }

    }

}