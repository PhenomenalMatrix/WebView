package com.example.webview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

@ObsoleteCoroutinesApi
class MainViewModel: ViewModel() {
           open var liveData = MutableLiveData<String>()
    init {
        viewModelScope.launch(newSingleThreadContext("ParseOrion")) {

            var doc: Document? = null
                    var url: String? = null
            try {
                doc = Jsoup.connect("https://sportbetgames.ru/quizone").get()
                Log.e("ololo", "retrieveWebInfo: ${doc.body().html()}")
                url = doc.body().html()
                if (url !=null) {
                    liveData.postValue(url)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

}