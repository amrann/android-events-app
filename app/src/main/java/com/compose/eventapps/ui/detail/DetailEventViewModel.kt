package com.compose.eventapps.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.compose.eventapps.data.response.DetailEvent
import com.compose.eventapps.data.response.DetailEventResponse
import com.compose.eventapps.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel : ViewModel() {
  private val _detailEvent = MutableLiveData<DetailEvent>()
  val detailEvent: LiveData<DetailEvent> = _detailEvent

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  companion object {
    private const val TAG = "DetailViewModel"
  }

  fun getDetailEvent(idEvent: Int) {
    _isLoading.value = true
    val client = ApiConfig.getApiService().getDetailEvent(idEvent)
    client.enqueue(object : Callback<DetailEventResponse> {
      override fun onResponse(
        call: Call<DetailEventResponse?>,
        response: Response<DetailEventResponse?>
      ) {
        _isLoading.value = false
        if (response.isSuccessful) {
          _detailEvent.value = response.body()?.event
        } else {
          Log.e(TAG, "onFailure: ${response.message()}")
        }
      }

      override fun onFailure(call: Call<DetailEventResponse?>, t: Throwable) {
        _isLoading.value = false
        Log.e(TAG, "onFailure: ${t.message.toString()}")
      }
    })
  }
}

