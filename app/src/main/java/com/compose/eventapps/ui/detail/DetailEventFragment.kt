package com.compose.eventapps.ui.detail

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.compose.eventapps.data.response.DetailEvent
import com.compose.eventapps.databinding.FragmentDetailEventBinding
import androidx.core.net.toUri

class DetailEventFragment : Fragment() {

  private var _binding: FragmentDetailEventBinding? = null
  private val binding get() = _binding!!

  private val viewModelDetail: DetailEventViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    val dataId = DetailEventFragmentArgs.fromBundle(requireArguments()).extraDataDetail

    _binding = FragmentDetailEventBinding.inflate(inflater, container, false)
    val root: View = binding.root

    viewModelDetail.getDetailEvent(dataId)

    viewModelDetail.detailEvent.observe(viewLifecycleOwner) { dataDetailEvent ->
      setDataDetailEvent(dataDetailEvent)
    }

    viewModelDetail.isLoading.observe(viewLifecycleOwner) {
      showLoading(it)
    }

    return root
  }

  private fun setDataDetailEvent(detailEvent: DetailEvent) {
    _binding?.apply {
      Glide.with(this@DetailEventFragment)
        .load(detailEvent.mediaCover)
        .into(imgBanner)
      tvTitle.text = detailEvent.name
      tvOwnerName.text = detailEvent.ownerName
      tvSubtitle.text = detailEvent.summary
      val sisaQuota = detailEvent.quota - detailEvent.registrants
      tvQuota.text = sisaQuota.toString()
      tvDate.text = detailEvent.endTime
      tvDescription.text = HtmlCompat.fromHtml(
        detailEvent.description,
        HtmlCompat.FROM_HTML_MODE_LEGACY
      )
      val url = detailEvent.link
      if (sisaQuota <= 0) {
        btnRegister.isEnabled = false
        btnRegister.alpha = 0.5f
      } else {
        btnRegister.isEnabled = true
        btnRegister.alpha = 1f
        btnRegister.setOnClickListener {
          val intent = Intent(Intent.ACTION_VIEW)
          intent.data = url.toUri()
          startActivity(intent)
        }
      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}