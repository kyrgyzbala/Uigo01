package kg.kyrgyzcoder.kassa01.ui.sell

import android.content.Context
import android.util.SparseArray
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

class LimitToOneBarcodeDetector(context: Context) : Detector<Barcode>() {
    private val internalDetector: BarcodeDetector = BarcodeDetector.Builder(context)
        .setBarcodeFormats(Barcode.ALL_FORMATS)
        .build()

    override fun detect(frame: Frame): SparseArray<Barcode> {
        val detected = this.internalDetector.detect(frame)
            ?: // Nothing detected, kick out an empty array
            return SparseArray()
        if (detected.size() == 0) {
            // Detected size = 0 , kick out an empty array
            return SparseArray()
        }
        if (detected.size() > 1) {
            // Detected more than one barcode, kick out an empty array
            return SparseArray()
        }
        // If we're here, there is only one barcode, return the array
        return detected
    }
}