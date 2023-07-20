package com.example.appbudgetv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.appbudgetv2.databinding.FragmentAdicionarDespesaBinding


class AdicionarDespesaFragment : Fragment() {
    private var despesa:Despesa?=null
    private var _binding: FragmentAdicionarDespesaBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdicionarDespesaBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_guardar -> {
                cancelar()
                true
            }
            else -> false
        }
    }
    private fun cancelar() {
        voltarListaDespesa()
    }

    private  fun voltarListaDespesa(){
        findNavController().navigate(R.id.action_adicionarDespesaFragment_to_listaDespesasFragment)
    }

    private fun guardar() {
        val nomeDespesa = binding.TextInputEditTextNomeDespesa.text.toString()
        if (nomeDespesa.isBlank()) {
            binding.TextInputEditTextNomeDespesa.error = getString(R.string.RequestNameExpense)
            binding.TextInputEditTextNomeDespesa.requestFocus()
            return
        }
        if (nomeDespesa.length > 30) {
            binding.TextInputEditTextNomeDespesa.error = getString(R.string.Aviso_NameExpense)
            binding.TextInputEditTextNomeDespesa.requestFocus()
            return
        }
        val tipo = binding.TextInputEditTextTipo.text.toString()
        if (tipo.isBlank()) {
            binding.TextInputEditTextTipo.error = getString(R.string.RequestTypeExpense)
            binding.TextInputEditTextTipo.requestFocus()
            return
        }
        if (tipo.length > 30) {
            binding.TextInputEditTextTipo.error = getString(R.string.Aviso_TypeExpense)
            binding.TextInputEditTextTipo.requestFocus()
            return
        }
        val ValorAcumulado = 0.0

        val ValorTotal = binding.TextInputEditTextValorTotal.text.toString()
        if (ValorTotal.isBlank()) {
            binding.TextInputEditTextValorTotal.error = getString(R.string.RequestAmount)
            binding.TextInputEditTextValorTotal.requestFocus()
            return
        }


        val despesas = Despesa(
            nomeDespesa,
            tipo,
            ValorAcumulado,
            ValorTotal.toDouble(),
        )

        insereDespesa(despesas)
    }


    private fun insereDespesa(
        despesa: Despesa
    ){
        val id = requireActivity().contentResolver.insert(
            BudgetContentProvider.ENDERECO_DESPESA,
            despesa.toContentValues()
        )

        if (id == null) {
            binding.TextInputEditTextNomeDespesa.error = getString(R.string.ErrorExpenseNew)
            return
        }
        Toast.makeText(requireContext(), getString(R.string.AcertExpenseNew), Toast.LENGTH_SHORT).show()
        voltarListaDespesa()

    }

    companion object {

    }
}