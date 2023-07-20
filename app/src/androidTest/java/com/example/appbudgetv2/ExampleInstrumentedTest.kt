package com.example.appbudgetv2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.Calendar

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    /*@Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.appbudgetv2", appContext.packageName)
    }*/
    private fun getAppContext(): Context =
        InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun apagaBaseDados() {
        //getAppContext().deleteDatabase(BudgetOpenHelper.NOME_BASE_DE_DADOS)
    }

    @Test
    fun consegueLerDespesas() {
        val bd = getWritableDatabase()

        val despesa1 = Despesa("Lidl","Semanal",150.5,1500.99)
        insereDespesa(bd, despesa1)

        val despesa2 = Despesa("Pingo Doce","Semanal",150.5,1500.99)
        insereDespesa(bd, despesa2)


        val cursor = TabelaDespesas(bd).consulta(
            TabelaDespesas.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(despesa1.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val despesaBD = Despesa.fromCursor(cursor)

        assertEquals(despesa1, despesaBD)

        val tabelaDespesas = TabelaDespesas(bd)
        val cursorTodasDespesas = tabelaDespesas.consulta(
            TabelaDespesas.CAMPOS,
            null, null, null, null,
            TabelaDespesas.CAMPO_NOME_DESPESA
        )

        assert(cursorTodasDespesas.count > 1)
    }

    @Test
    fun consegueLerItens() {
        val bd = getWritableDatabase()

        val despesa = Despesa("Lidl","Semanal",150.5,1500.99)
        insereDespesa(bd, despesa)

        val data = Calendar.getInstance()
        data.set(2022, 4, 1)

        val item = Item("Compras de carne", 30.0,data,despesa)
        insereItem(bd, item)

        val data2 = Calendar.getInstance()
        data2.set(2023, 1, 1)

        val item2 = Item("Compras de Peixe", 30.0,data2,despesa)
        insereItem(bd, item2)

        val tabelaItens = TabelaItens(bd)

        val cursor = tabelaItens.consulta(
            TabelaItens.CAMPOS,
            "${TabelaItens.CAMPO_ID}=?",
            arrayOf(item.id.toString()),
            null,
            null,
            null
        )

        //cursor.moveToFirst()
        assert(cursor.moveToNext())

        val ItemBD = Item.fromCursor(cursor)

        assertEquals(item, ItemBD)

        val cursorTodosItens = tabelaItens.consulta(
            TabelaItens.CAMPOS,
            null, null, null, null,
            TabelaItens.CAMPO_NOME
        )

        assert(cursorTodosItens.count > 1)
    }
    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BudgetOpenHelper(getAppContext())
        val bd = openHelper.readableDatabase
        assert(bd.isOpen)

        val appContext = getAppContext()
        assertEquals("com.example.appbudgetv2", appContext.packageName)
    }

    @Test
    fun consegueInserirDespesas() {
        val bd = getWritableDatabase()

        val despesa = Despesa("Supermercado","Semanal",150.5,1500.99)
        val id = insereDespesa(bd, despesa)
        assertNotEquals(-1, id)
    }
    private fun insereDespesa(
        bd: SQLiteDatabase,
        despesa: Despesa
    ) {
        despesa.id = TabelaDespesas(bd).insere(despesa.toContentValues())
        assertNotEquals(-1, despesa.id)
    }

    @Test
    fun consegueInserirItem() {
        val bd = getWritableDatabase()

        val data2 = Calendar.getInstance()
        data2.set(2022, 4, 1)

        val despesa2 = Despesa("Ginásio","Semanal",150.5,1500.99)
        insereDespesa(bd, despesa2)

        val item1 = Item("Mensalidade ginásio", 30.0,data2,despesa2)
        insereItem(bd, item1)

        val data3= Calendar.getInstance()
        data3.set(2020, 3, 1)
        val item2 = Item("Tubias", 35.0, data3, despesa2 )
        insereItem(bd, item2)

    }

    private fun insereItem(
        bd: SQLiteDatabase,
        item: Item
    ) {
        item.id = TabelaItens(bd).insere(item.toContentValues())
        assertNotEquals(-1, item.id)
    }

    @Test
    fun consegueAlterarDespesa() {
        val bd = getWritableDatabase()

        val despesa = Despesa("Domestica","Semestral",4500.5,15000.99)
        insereDespesa(bd, despesa)

        despesa.tipo = "Mensal"

        val registosAlterados = TabelaDespesas(bd).altera(
            despesa.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(despesa.id.toString())
        )

        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueAlterarItem() {
        val bd = getWritableDatabase()

        val despesa2 = Despesa("Cão","Semestral",4500.5,15000.99)
        insereDespesa(bd, despesa2)

        val Data3 = Calendar.getInstance()
        Data3.set(2022, 5, 1)

        val item2 = Item("Veterinário", 30.0,Data3,despesa2)
        insereItem(bd, item2)

        val novaData = Calendar.getInstance()
        novaData.set(2022, 6, 1)


        item2.data = novaData


        val registosAlterados = TabelaItens(bd).altera(
            item2.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(item2.id.toString())
        )

        assertEquals(1, registosAlterados)
    }




    @Test
    fun consegueApagarDespesas() {
        val bd = getWritableDatabase()

        val categoria = Despesa("Diversão","Semestral",144.5,1405.99)
        insereDespesa(bd, categoria)

        val registosEliminados = TabelaDespesas(bd).elimine(
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString())
        )

        assertEquals(1, registosEliminados)
    }


    @Test
    fun consegueApagarItens() {
        val bd = getWritableDatabase()

        val despesa = Despesa("Universidade","Semestral",679.00,1405.99)
        insereDespesa(bd, despesa)


        val Data3 = Calendar.getInstance()
        Data3.set(2022, 5, 1)

        val item3 = Item("Propinas", 30.0,Data3,despesa)
        insereItem(bd, item3)


        val registosEliminados = TabelaItens(bd).elimine(
            "${BaseColumns._ID}=?",
            arrayOf(item3.id.toString())
        )

        assertEquals(1, registosEliminados)
    }


    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BudgetOpenHelper(getAppContext())
        return openHelper.writableDatabase
    }

}