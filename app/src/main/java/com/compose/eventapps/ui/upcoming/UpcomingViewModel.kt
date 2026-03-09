package com.compose.eventapps.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.compose.eventapps.data.response.EventResponse
import com.compose.eventapps.data.response.ListEventsItem
import com.compose.eventapps.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

  private val _listUpcomingEvent = MutableLiveData<List<ListEventsItem>>()
  val listUpcomingEvent: LiveData<List<ListEventsItem>> = _listUpcomingEvent

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  private val _errorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String> = _errorMessage

  companion object {
    private const val TAG = "UpcomingViewModel"
  }

  fun getListEvent() {
    _isLoading.value = true
    val client = ApiConfig.getApiService().getEvents(0, "", 40)
    client.enqueue(object : Callback<EventResponse> {
      override fun onResponse(call: Call<EventResponse?>, response: Response<EventResponse?>) {
        _isLoading.value = false
        if (response.isSuccessful) {
          _listUpcomingEvent.value = response.body()?.listEvents
        } else {
          Log.e(TAG, "onFailure: ${response.message()}")
        }
      }

      override fun onFailure(
        call: Call<EventResponse?>,
        t: Throwable
      ) {
        _isLoading.value = false
        _errorMessage.value = t.message ?: "Terjadi kesalahan"
        Log.e(TAG, "onFailure: ${t.message.toString()}")
      }
    })
  }
}