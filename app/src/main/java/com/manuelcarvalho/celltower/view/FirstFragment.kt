package com.manuelcarvalho.celltower.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.manuelcarvalho.celltower.R
import com.manuelcarvalho.cocopic.viewmodel.AppViewModel



private const val TAG = "FirstFragment"
class FirstFragment : Fragment() {

    private lateinit var textview_first: TextView

    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[AppViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        observeViewModel()

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        textview_first = view.findViewById(R.id.textview_first)
    }

    fun observeViewModel() {

        viewModel.details.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                Log.d(TAG, "Fragment ${list[0]} ")
                var sig = list[0]
                textview_first.text = sig.asuLevel.toString()

            }
        })


    }


}