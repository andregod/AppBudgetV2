package com.example.appbudgetv2

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager

private const val ID_LOADER_LIVROS = 0

private val adapterDespesas: AdapterDespesas
    get() {
        val adapterDespesas = AdapterDespesas()
        return adapterDespesas
    }

/**
 * A simple [Fragment] subclass.
 * Use the [ListaDespesasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaDespesasFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor>  {

    private var _binding: FragmentListaDespesasBinding? = null
    private val binding get() = _binding!!
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var adapterDespesas = AdapterDespesas? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterDespesas = AdapterDespesas(this)
        binding.recyclerViewDespesas.adapter = adapterDespesas
        binding.recyclerViewDespesas.layoutManager = LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_LIVROS, null, this)
    }
    companion object {

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