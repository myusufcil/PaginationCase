package com.myusufcil.casestudy.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.myusufcil.casestudy.adapter.CaseAdapter
import com.myusufcil.casestudy.base.BaseFragment
import com.myusufcil.casestudy.databinding.FragmentListBinding
import com.myusufcil.casestudy.extensions.invisible
import com.myusufcil.casestudy.extensions.visible
import com.myusufcil.casestudy.ui.list.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ListFragment :
    BaseFragment<ListViewModel, FragmentListBinding>(FragmentListBinding::inflate) {

    private val caseAdapter = CaseAdapter()

    override val viewModel: ListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        handleEvents()
        getPersonsFromLocal()
    }

    private fun getPersonsFromLocal() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getPersonList().collectLatest { _person ->
                caseAdapter.submitData(_person)
            }
        }
    }

    private fun handleEvents() {
        binding?.swipeRefreshLayout?.setOnRefreshListener {
            caseAdapter.refresh()
        }
    }

    private fun setUpAdapter() {
        binding?.rvPersons?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        caseAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    if (caseAdapter.snapshot().isEmpty()) {
                        binding?.progress?.isVisible = true
                    }
                    binding?.errorTxt?.invisible()
                }
                else -> {
                    binding?.progress?.invisible()
                    binding?.swipeRefreshLayout?.isRefreshing = false
                    val error = when {
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }
                    error?.let {
                        if (caseAdapter.snapshot().isEmpty()) {
                            binding?.errorTxt?.visible()
                            binding?.errorTxt?.text = it.error.localizedMessage
                        }
                    }
                }
            }
        }
        binding?.rvPersons?.adapter = caseAdapter
    }
}