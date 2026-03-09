package com.compose.eventapps.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.compose.eventapps.adapter.ListEventsAdapter
import com.compose.eventapps.data.response.ListEventsItem
import com.compose.eventapps.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {

  private var _binding: FragmentFinishedBinding? = null

  private lateinit var rvFinishedEvent: RecyclerView

  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    val finishedViewModel = ViewModelProvider(this)[FinishedViewModel::class.java]

    _binding = FragmentFinishedBinding.inflate(inflater, container, false)
    val root: View = binding.root

    rvFinishedEvent = _binding?.rvFinishedEvent!!
    rvFinishedEvent.setHasFixedSize(true)

    finishedViewModel.getListEvent()

    rvFinishedEvent.layoutManager = LinearLayoutManager(requireActivity())
    finishedViewModel.listFinishedEvent.observe(viewLifecycleOwner) { finishedData ->
      setDataEvent(finishedData as ArrayList<ListEventsItem>)
    }

    finishedViewModel.isLoading.observe(viewLifecycleOwner) {
      showLoading(it)
    }

    finishedViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
      Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    return root
  }

  private fun setDataEvent(listEventsItem: ArrayList<ListEventsItem>) {
    val listEventsAdapter = ListEventsAdapter(listEventsItem)
    rvFinishedEvent.adapter = listEventsAdapter

    listEventsAdapter.setOnItemClickCallback(object : ListEventsAdapter.OnItemClickCallback {
      override fun onItemClicked(data: ListEventsItem) {
        showSelectedTeam(data.id)
      }
    })
  }

  private fun showSelectedTeam(dataId: Int) {
    val action = FinishedFragmentDirections.actionFinishedToDetail(dataId)
    findNavController().navigate(action)
  }

  private fun showLoading(isLoading: Boolean) {
    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}