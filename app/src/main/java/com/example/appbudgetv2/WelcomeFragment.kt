package com.example.appbudgetv2

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appbudgetv2.databinding.FragmentWelcomeBinding


/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Hide the toolbar when this fragment is displayed
        (activity as? MainActivity)?.hideToolbar()

        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Declaring and initializing
        // the elements from the layout file
        binding.buttonSlide.setOnClickListener {
            //(activity as MainActivity)?.loadFragment(HomeFragment())
            findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
       }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        // Re-enable the toolbar when the fragment is destroyed
        (activity as? MainActivity)?.showToolbar()

        _binding = null


    }


}