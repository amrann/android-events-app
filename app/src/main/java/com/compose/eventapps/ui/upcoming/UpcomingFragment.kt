package com.compose.eventapps.ui.upcoming

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
import com.compose.eventapps.databinding.FragmentUpcomingBinding

class UpcomingFragment : Fragment() {

  private var _binding: FragmentUpcomingBinding? = null

  private lateinit var rvUpcomingEvent: RecyclerView
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val upcomingViewModel = ViewModelProvider(this)[UpcomingViewModel::class.java]

    _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
    val root: View = binding.root

    rvUpcomingEvent = _binding?.rvUpcomingEvent!!
    rvUpcomingEvent.setHasFixedSize(true)

    upcomingViewModel.getListEvent()

    rvUpcomingEvent.layoutManager = LinearLayoutManager(requireActivity())
    upcomingViewModel.listUpcomingEvent.observe(viewLifecycleOwner) { upcomingData ->
      setDataEvent(upcomingData as ArrayList<ListEventsItem>)
    }

    upcomingViewModel.isLoading.observe(viewLifecycleOwner) {
      showLoading(it)
    }

    upcomingViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
      Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    return root
  }

  private fun setDataEvent(listEventsItem: ArrayList<ListEventsItem>) {
    val listEventsAdapter = ListEventsAdapter(listEventsItem)
    rvUpcomingEvent.adapter = listEventsAdapter

    listEventsAdapter.setOnItemClickCallback(object : ListEventsAdapter.OnItemClickCallback {
      override fun onItemClicked(data: ListEventsItem) {
        showSelectedTeam(data.id)
      }
    })
  }

  private fun showSelectedTeam(dataId: Int) {
    val action = UpcomingFragmentDirections.actionUpcomingToDetail(dataId)
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