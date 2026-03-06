package com.compose.eventapps.ui.finished

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

class FinishedViewModel : ViewModel() {

//  private val _text = MutableLiveData<String>().apply {
//    value = "This is finished Fragment"
//  }
//  val text: LiveData<String> = _text

  private val _listEvent = MutableLiveData<List<ListEventsItem>>()
  val listEvent: LiveData<List<ListEventsItem>> = _listEvent

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  companion object {
    private const val TAG = "FinishedViewModel"
  }

  private fun getListEvent(active: Int, q: String, limit: Int) {
    _isLoading.value = true
    val client = ApiConfig.getApiService().getRestaurant(active, q, limit)
    client.enqueue(object : Callback<EventResponse> {
      override fun onResponse(call: Call<EventResponse?>, response: Response<EventResponse?>) {
        _isLoading.value = false
        if (response.isSuccessful) {
          _listEvent.value = response.body()?.listEvents
        } else {
          Log.e(TAG, "onFailure: ${response.message()}")
        }
      }

      override fun onFailure(
        call: Call<EventResponse?>,
        t: Throwable
      ) {
        _isLoading.value = false
        Log.e(TAG, "onFailure: ${t.message.toString()}")
      }
    })

  }


//  private fun findRestaurant() {
//    _isLoading.value = true
//    val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
//    client.enqueue(object : Callback<RestaurantResponse> {
//      override fun onResponse(
//        call: Call<RestaurantResponse>,
//        response: Response<RestaurantResponse>
//      ) {
//        _isLoading.value = false
//        if (response.isSuccessful) {
//          _restaurant.value = response.body()?.restaurant
//          _listReview.value = response.body()?.restaurant?.customerReviews
//        } else {
//          Log.e(TAG, "onFailure: ${response.message()}")
//        }
//      }
//
//      override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
//        _isLoading.value = false
//        Log.e(TAG, "onFailure: ${t.message.toString()}")
//      }
//    })
//  }
}