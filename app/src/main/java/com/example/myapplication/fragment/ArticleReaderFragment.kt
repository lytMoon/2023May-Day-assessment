package com.example.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.MyViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.FragmentNewsReaderBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ArticleReaderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleReaderFragment : Fragment() {
    private lateinit var webView: WebView
    //懒加载注入databinding
    private val mBinding: FragmentNewsReaderBinding by lazy { FragmentNewsReaderBinding.inflate(layoutInflater) }

    //懒加载注入viewmodel
    private val myViewModel by lazy {
        ViewModelProvider(this).get(MyViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView=mBinding.webView
        webView.webViewClient = WebViewClient()

        val url = arguments?.getString("url")
        if (!url.isNullOrEmpty()) {
            webView.loadUrl(url)
        }

        view.findViewById<ImageButton>(R.id.commentButton).setOnClickListener {

            // TODO: Add comment button click event handling
        }

        view.findViewById<ImageButton>(R.id.favoriteButton).setOnClickListener {

            // TODO: Add favorite button click event handling
        }

        view.findViewById<ImageButton>(R.id.shareButton).setOnClickListener {

            // TODO: Add share button click event handling
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mBinding.root
    }


}