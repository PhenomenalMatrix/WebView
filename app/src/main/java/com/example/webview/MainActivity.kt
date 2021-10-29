package com.example.webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.example.webview.databinding.ActivityMainBinding
import android.webkit.WebResourceRequest

import android.webkit.WebView

import android.annotation.TargetApi
import android.os.*
import android.util.Log

import android.webkit.WebViewClient
import android.os.StrictMode.ThreadPolicy
import android.view.KeyEvent
import android.webkit.WebSettings
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private var binding: ActivityMainBinding? = null
    private var mainViewModel: MainViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        checkConnectivity()
        mainViewModel!!.liveData.observe(this, Observer {
            initWebView(it)
        })

    }

    fun showFragment() {
        Thread.sleep(1000)
        binding?.progressBar?.visibility = View.GONE
        binding!!.fragmentContainer.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, QuizFragment()).commit()
    }

    fun hideFragment() {
        binding!!.fragmentContainer.visibility = View.GONE
        supportFragmentManager.beginTransaction().remove(QuizFragment()).commit()
    }


    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    private fun checkConnectivity() {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activityNetwork = cm.activeNetworkInfo
        val isConnected = activityNetwork != null && activityNetwork.isConnectedOrConnecting
        showLayouts(isConnected)
    }

    private fun showLayouts(isConnected: Boolean) {
        if (!isConnected) {
            binding?.webViewItem?.visibility = View.GONE
            binding?.noConnection?.root?.visibility = View.VISIBLE
        } else {
            binding?.webViewItem?.visibility = View.VISIBLE
            binding?.noConnection?.root?.visibility = View.GONE
            initWebView()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView(url: String? = null) {
        if (!url.isNullOrEmpty()) {
            binding?.progressBar?.visibility = View.GONE
            hideFragment()
            Log.e("sad", "initWebView: $url")
            binding?.webViewItem?.settings?.javaScriptEnabled = true
            binding?.webViewItem?.settings?.domStorageEnabled = true
            binding?.webViewItem?.settings?.loadWithOverviewMode = true
            binding?.webViewItem?.settings?.useWideViewPort = true
            binding?.webViewItem?.settings?.builtInZoomControls = true
            binding?.webViewItem?.settings?.displayZoomControls = false
            binding?.webViewItem?.settings?.setSupportZoom(true)
            binding?.webViewItem?.settings?.defaultTextEncodingName = "utf-8"
            binding?.webViewItem?.settings?.pluginState = WebSettings.PluginState.ON
            url?.let { binding?.webViewItem?.loadUrl(it) }

            val webViewClient: WebViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return true
                }

                @TargetApi(Build.VERSION_CODES.N)
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    view.loadUrl(request.url.toString())
                    return true
                }
            }

            binding?.webViewItem?.webViewClient = webViewClient
        } else {
            showFragment()

        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showLayouts(isConnected)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (binding?.webViewItem != null && keyCode == KeyEvent.KEYCODE_BACK && binding?.webViewItem!!.canGoBack()
        ) {
            binding?.webViewItem!!.goBack()
            return false
        } else {
            return false
        }
    }


}