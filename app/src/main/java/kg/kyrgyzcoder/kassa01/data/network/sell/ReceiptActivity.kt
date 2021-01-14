package kg.kyrgyzcoder.kassa01.data.network.sell

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.databinding.ActivityReceiptBinding
import kg.kyrgyzcoder.kassa01.util.EXTRA_RECEIPT

class ReceiptActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceiptBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        binding.arrBack.setOnClickListener {
            onBackPressed()
        }

        val html = intent.getIntExtra(EXTRA_RECEIPT, 0)

        if (html != 0) {
            binding.webViewReceipt.settings.javaScriptEnabled = true
            binding.webViewReceipt.settings.builtInZoomControls = true;
            binding.webViewReceipt.settings.setSupportZoom(true);
            binding.webViewReceipt.loadUrl("http://165.22.93.189/pyapps/venv/api/item/clientorder/$html")

            binding.webViewReceipt.webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest) =
                    false

                override fun onPageFinished(view: WebView, url: String) {
                    binding.buttonPrint.setOnClickListener {
                        createWebPrintJob(view)
                    }
                }
            }
        }

    }

    private fun createWebPrintJob(webView: WebView) {

        // Get a PrintManager instance
        (getSystemService(Context.PRINT_SERVICE) as? PrintManager)?.let { printManager ->

            val jobName = "${getString(R.string.app_name)} Document"

            // Get a print adapter instance
            val printAdapter = webView.createPrintDocumentAdapter(jobName)

            // Create a print job with name and adapter instance
            printManager.print(
                jobName,
                printAdapter,
                PrintAttributes.Builder().build()
            ).also { printJob ->
                Log.d("NURIKO", "createWebPrintJob: $printJob")
            }
        }
    }
}
