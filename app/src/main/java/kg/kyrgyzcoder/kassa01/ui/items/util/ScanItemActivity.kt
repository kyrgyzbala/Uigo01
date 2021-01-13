package kg.kyrgyzcoder.kassa01.ui.items.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelCategory
import kg.kyrgyzcoder.kassa01.databinding.ActivityScanItemBinding
import kg.kyrgyzcoder.kassa01.ui.sell.LimitToOneBarcodeDetector
import kg.kyrgyzcoder.kassa01.util.EXTRA_CATEGORY_ITEM
import kg.kyrgyzcoder.kassa01.util.REQUEST_CAMERA_CODE
import kg.kyrgyzcoder.kassa01.util.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class ScanItemActivity : AppCompatActivity(), CustomAddItemName.AddItemNameClickListener {

    private lateinit var binding: ActivityScanItemBinding

    private lateinit var cameraSource: CameraSource
    private lateinit var detector: LimitToOneBarcodeDetector
    private lateinit var mp: MediaPlayer

    private var category: ModelCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#f3f3f4")

        category = intent.getSerializableExtra(EXTRA_CATEGORY_ITEM) as ModelCategory

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askCameraPermission()
        } else {
            setupControls()
        }
    }

    private fun setupControls() {
        detector = LimitToOneBarcodeDetector(this)
        cameraSource = CameraSource.Builder(this, detector)
            .setAutoFocusEnabled(true)
            .build()
        binding.cameraSurfaceView.holder.addCallback(surfaceCallback)
        detector.setProcessor(processor)
        mp = MediaPlayer.create(this, R.raw.sound)
    }

    private fun askCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_CODE
        )
    }

    private val processor = object : Detector.Processor<Barcode> {
        override fun release() {
            Log.d("CAMERA11", "release: release")
            GlobalScope.launch(Dispatchers.Main) {
                delay(1000)

            }
        }

        @SuppressLint("MissingPermission")
        override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
            if (detections != null && detections.detectedItems.isNotEmpty()) {
                val qrCodes: SparseArray<Barcode> = detections.detectedItems
                val code = qrCodes.valueAt(0)
                binding.textViewScan.text = code.displayValue
                mp.start()
                goBack(code.displayValue)
                detector.release()
            } else {
                binding.textViewScan.text = "NULL"
            }
        }
    }

    private fun goBack(displayValue: String) {
        val dialog = CustomAddItemName(displayValue, this)
        dialog.show(supportFragmentManager, "AddItemName")
    }

    override fun confirmAddItem(itemCode: String, itemCost: Float, itemCount: Int) {
        toast("code: $itemCode, cost: $itemCost, count: $itemCount, category: ${category?.name}")
        Log.d(
            "NURR",
            "confirmAddItem: code: $itemCode, cost: $itemCost, count: $itemCount, category: ${category?.name}"
        )
        onBackPressed()
    }

    override fun cancelled() {
        onBackPressed()
    }

    private val surfaceCallback = object : SurfaceHolder.Callback {
        @SuppressLint("MissingPermission")
        override fun surfaceCreated(holder: SurfaceHolder) {
            try {
                cameraSource.start(holder)
            } catch (e: Exception) {
                toast("Ошибка : ${e.message}")
                Log.d("CAMERA", "surfaceCreated: ${e.message}")
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            cameraSource.stop()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CAMERA_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                toast(getString(R.string.allowCameraUse))
            }
        }
    }

}