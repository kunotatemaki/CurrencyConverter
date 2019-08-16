package com.raul.androidapps.softwaretestrevolut.ui.selector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.raul.androidapps.softwaretestrevolut.R
import com.raul.androidapps.softwaretestrevolut.databinding.FragmentSelectorBinding
import com.raul.androidapps.softwaretestrevolut.ui.common.BaseFragment

class SelectorFragment : BaseFragment() {

    private lateinit var binding: FragmentSelectorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_selector,
            container,
            false,
            bindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.coroutinesButton.setOnClickListener {
            findNavController().navigate(
                SelectorFragmentDirections.actionSelectorFragmentToConversionFragment(true)
            )
        }
        binding.rxJavaButton.setOnClickListener {
            findNavController().navigate(
                SelectorFragmentDirections.actionSelectorFragmentToConversionFragment(false)
            )
        }
    }

}
