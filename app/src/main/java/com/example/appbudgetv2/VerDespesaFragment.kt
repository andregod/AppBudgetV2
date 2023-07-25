package com.example.appbudgetv2

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appbudgetv2.databinding.FragmentVerDespesaBinding


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
        //activity.idMenuAtual = R.menu.menu_eliminar

        despesa = VerDespesaFragmentArgs.fromBundle(requireArguments()).despesa

        binding.textViewVerNomeDespesa.text = despesa.nomeDespesa
        binding.textViewVerTipoDespesa.text = despesa.tipo
        binding.textViewVerValorDespesa.text = despesa.valorAcumulado.toString()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }
}