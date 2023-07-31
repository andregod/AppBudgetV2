package com.example.appbudgetv2

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
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




        binding.NomeDespesa.setText(despesa.nomeDespesa)
        binding.textViewVerTipoDespesa.setText(despesa.tipo)
        binding.textViewVerValorMaximoDespesa.setText(despesa.valorTotal.toString())
        binding.textViewVerValorDespesa.setText(despesa.valorAcumulado.toString())

        val valorAcumulado = binding.textViewVerValorDespesa
        valorAcumulado.isEnabled = false


        val NomeDespesa = binding.NomeDespesa
        NomeDespesa.isEnabled = false

        val textViewVerTipoDespesa = binding.textViewVerTipoDespesa
        textViewVerTipoDespesa.isEnabled = false

        val textViewVerValorMaximoDespesa = binding.textViewVerValorMaximoDespesa
        textViewVerValorMaximoDespesa.isEnabled = false

        val BotaoNomeDespesa=binding.buttonWithIcon

        BotaoNomeDespesa.setOnClickListener {
            NomeDespesa.isEnabled = true
        }

        val BotaotextViewVerTipoDespesa=binding.buttonWithIcon2

        BotaotextViewVerTipoDespesa.setOnClickListener {
            textViewVerTipoDespesa.isEnabled = true
        }

        val BotaotextViewVerValorDespesa=binding.buttonWithIcon3

        BotaotextViewVerValorDespesa.setOnClickListener {
            textViewVerValorMaximoDespesa.isEnabled = true
        }

        val rootView = binding.root
        rootView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Salva as alterações feitas nos campos de texto
                salvarAlteracoes()
                // Desabilita a edição dos campos de texto novamente
                desabilitarEdicaoCampos()
            }
            true // Retorna true para indicar que o evento de toque foi tratado

    }



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
            Snackbar.make(binding.NomeDespesa, getString(R.string.erro_eliminar_Despesa), Snackbar.LENGTH_INDEFINITE)
        }
    }

    private fun voltaListaDespesa() {
        findNavController().navigate(R.id.action_verDespesaFragment_to_listaDespesasFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun desabilitarEdicaoCampos() {
        binding.NomeDespesa.isEnabled = false
        binding.textViewVerTipoDespesa.isEnabled = false
        binding.textViewVerValorMaximoDespesa.isEnabled = false
    }
    private fun salvarAlteracoes(){
        val NomeDespesa = binding.NomeDespesa.text.toString()
        val textViewVerTipoDespesa = binding.textViewVerTipoDespesa.text.toString()
        val textViewVerValorMaximoDespesa = binding.textViewVerValorMaximoDespesa.text.toString().toDouble()

        if( despesa.nomeDespesa!=NomeDespesa || despesa.tipo!=textViewVerTipoDespesa ||despesa.valorAcumulado != textViewVerValorMaximoDespesa) {
            val despesa = despesa!!
            despesa.nomeDespesa = NomeDespesa
            despesa.tipo = textViewVerTipoDespesa
            despesa.valorAcumulado = despesa.valorAcumulado
            despesa.valorTotal=textViewVerValorMaximoDespesa

            alteraDespesa(despesa)
        }
    }
    private fun alteraDespesa(despesa: Despesa) {
        val enderecoDespesa= Uri.withAppendedPath(BudgetContentProvider.ENDERECO_DESPESA, despesa.id.toString())
        val despesaAlterados = requireActivity().contentResolver.update(enderecoDespesa, despesa.toContentValues(), null, null)

        if (despesaAlterados == 1) {
            Toast.makeText(requireContext(), R.string.AcertExpenseNew, Toast.LENGTH_LONG).show()

        } else {
            binding.NomeDespesa.error = getString(R.string.ErrorExpenseNew)
        }
    }
    companion object {

    }
}