package com.example.appbudgetv2

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.appbudgetv2.databinding.FragmentVerDespesaBinding
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 * Use the [VerDespesaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VerDespesaFragment : Fragment() {

    private lateinit var despesa: Despesa
    private var _binding: FragmentVerDespesaBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVerDespesaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_lista2

        despesa = VerDespesaFragmentArgs.fromBundle(requireArguments()).despesa

        binding.textViewVerNomeDespesa.text = despesa.nomeDespesa
        binding.textViewVerTipoDespesa.text = despesa.tipo
        binding.textViewVerValorDespesa.text = despesa.valorAcumulado.toString()

    }
    fun processaOpcaoMenu(item: MenuItem) : Boolean {
        return when (item.itemId) {
            R.id.action_Add -> {
                adiciona()
                true
            }
            R.id.action_Edit -> {
               // edita()
                true
            }
            R.id.action_Delete -> {
                val dialogoAlerta= AlertDialog.Builder(requireContext())
                dialogoAlerta.setMessage(R.string.Aviso_Eliminar_Despesa)
                dialogoAlerta.setTitle(R.string.Eliminar_Despesa_label)
                dialogoAlerta.setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialogInterface, witch ->elimina()  })
                dialogoAlerta.setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, witch -> })
                dialogoAlerta.show()

                true
            }
            else -> false
        }
    }

    private fun adiciona() {
        val acao = VerDespesaFragmentDirections.actionVerDespesaFragmentToAdicionarDespesaFragment(null)
        findNavController().navigate(acao)
    }

    private fun elimina() {
        val enderecoDespesa = Uri.withAppendedPath(BudgetContentProvider.ENDERECO_DESPESA, despesa.id.toString())
        val numDespesaEliminados = requireActivity().contentResolver.delete(enderecoDespesa, null, null)

        if (numDespesaEliminados == 1) {
            Toast.makeText(requireContext(), getString(R.string.Despesa_eliminado_com_sucesso), Toast.LENGTH_LONG).show()
            voltaListaDespesa()
        } else {
            Snackbar.make(binding.textViewVerNomeDespesa, getString(R.string.erro_eliminar_Despesa), Snackbar.LENGTH_INDEFINITE)
        }
    }

    private fun voltaListaDespesa() {
        findNavController().navigate(R.id.action_verDespesaFragment_to_listaDespesasFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }
}