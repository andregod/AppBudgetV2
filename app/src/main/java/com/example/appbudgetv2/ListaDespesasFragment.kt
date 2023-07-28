package com.example.appbudgetv2

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appbudgetv2.databinding.FragmentListaDespesasBinding

private const val ID_LOADER_DESPESAS = 0

/**
 * A simple [Fragment] subclass.
 * Use the [ListaDespesasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaDespesasFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor>  {

    private var _binding: FragmentListaDespesasBinding? = null
    private val binding get() = _binding!!

    var despesaSelecionada : Despesa? = null
        set(value) {
            field = value

            //val mostrarEliminarAlterar = (value != null)

            //val activity = activity as MainActivity
            //activity.mostraOpcaoMenu(R.id.action_editar, mostrarEliminarAlterar)

        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaDespesasBinding.inflate(inflater, container, false)


        return binding.root
    }

    private var adapterDespesas : AdapterDespesas? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       adapterDespesas = AdapterDespesas(this)

        binding.recyclerViewDespesas.adapter = adapterDespesas
        binding.recyclerViewDespesas.layoutManager = LinearLayoutManager(requireContext())



        val activity= activity as MainActivity
        activity.fragment=this
        activity.idMenuAtual=R.menu.menu_lista


        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_DESPESAS, null, this)
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean {
        return when (item.itemId) {
            R.id.action_Add -> {
                adicionaDespesa()
                true
            }

            else -> false
        }

    }

    private fun verDespesa() {
        val acao = ListaDespesasFragmentDirections.actionListaDespesasFragmentToVerDespesaFragment(despesaSelecionada!!)
        findNavController().navigate(acao)
    }
    private fun adicionaDespesa() {
        val acao = ListaDespesasFragmentDirections.actionListaDespesasFragmentToAdicionarDespesaFragment(null)
        findNavController().navigate(acao)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            BudgetContentProvider.ENDERECO_DESPESA,
            TabelaDespesas.CAMPOS,
            null, null,
            TabelaDespesas.CAMPO_NOME_DESPESA
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterDespesas!!.cursor = null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterDespesas!!.cursor = data
    }


}