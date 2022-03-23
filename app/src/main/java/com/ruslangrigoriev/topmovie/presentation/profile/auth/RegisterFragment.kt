package com.ruslangrigoriev.topmovie.presentation.profile.auth

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            webviewRegister.loadUrl("https://www.themoviedb.org/signup")
            webviewRegister.settings.javaScriptEnabled = true
            webviewRegister.canGoBack()
            webviewRegister.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (URLUtil.isNetworkUrl(url)) {
                        return false
                    }
                    try {
                        val shareIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(shareIntent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            requireContext(),
                            "Подходящее приложение не найдено ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return true
                }
            }
            webviewRegister.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.action == MotionEvent.ACTION_UP
                    && webviewRegister.canGoBack()
                ) {
                    webviewRegister.goBack()
                    return@OnKeyListener true
                }
                false
            })
        }
    }
}

