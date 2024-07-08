package com.example.techexactly.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techexactly.adapters.ApplicationsAdapter
import com.example.techexactly.api.Resource
import com.example.techexactly.databinding.FragmentApplicationsBinding
import com.example.techexactly.models.App
import com.example.techexactly.utils.Global
import com.example.techexactly.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApplicationsFragment : Fragment() {

    private val tAG = "ApplicationsFragment"
    private lateinit var binding: FragmentApplicationsBinding
    private lateinit var applicationsAdapter: ApplicationsAdapter
    private lateinit var appList: ArrayList<App>
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplicationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appList = ArrayList()
        setApplicationsRecycler()

        if (Global.appList.isNotEmpty()) {
            appList.clear()
            appList.addAll(Global.appList)
            binding.progressbar.visibility = GONE
            binding.tvNoData.visibility = GONE
            applicationsAdapter.differ.submitList(appList)
        } else {
            //App List
            viewModel.appList("378")
        }

        //App List Observer
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.appListFlow.collect {
                when (it) {
                    is Resource.LOADING -> {
                        binding.progressbar.visibility = VISIBLE
                    }

                    is Resource.ERROR -> {
                        binding.progressbar.visibility = GONE
                        Log.e(tAG, "App List Error : ${it.message}")
                    }

                    is Resource.SUCCESS -> {
                        binding.progressbar.visibility = GONE

                        val response = it.data

                        if (response.success) {
                            val appListData = response.data.app_list
                            if (appListData.isNotEmpty()) {
                                appList.clear()
                                appList.addAll(appListData)
                                if (appList.isNotEmpty()) {
                                    binding.tvNoData.visibility = GONE
                                    Global.appList.clear()
                                    Global.appList.addAll(appList)
                                    applicationsAdapter.differ.submitList(appList)
                                }
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }

        binding.edtSearch.addTextChangedListener {
            it?.let {
                if (appList.isNotEmpty()) {
                    if (it.isNotEmpty()) {
                        val tempList = ArrayList<App>()
                        for (app in appList) {
                            if (app.app_name.lowercase().startsWith(it.toString().lowercase())) {
                                tempList.add(app)
                            }
                        }
                        if(tempList.isNotEmpty()){
                            applicationsAdapter.differ.submitList(tempList)
                            binding.tvNoData.visibility= GONE
                        }else{
                            binding.tvNoData.visibility= VISIBLE
                        }
                    } else {
                        applicationsAdapter.differ.submitList(appList)
                        binding.tvNoData.visibility= GONE
                    }
                }
            }
        }
    }

    private fun setApplicationsRecycler() {
        applicationsAdapter = ApplicationsAdapter()
        binding.rvApplications.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = applicationsAdapter
        }
    }
}