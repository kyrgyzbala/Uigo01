package kg.kyrgyzcoder.kassa01.ui.sell

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelActive
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelActivePag
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelCategory
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelCategoryPag
import kg.kyrgyzcoder.kassa01.data.network.sell.ReceiptActivity
import kg.kyrgyzcoder.kassa01.data.network.sell.model.ModelPostTransaction
import kg.kyrgyzcoder.kassa01.data.network.sell.model.ModelSellItem
import kg.kyrgyzcoder.kassa01.databinding.FragmentSellMainBinding
import kg.kyrgyzcoder.kassa01.ui.items.util.ItemListener
import kg.kyrgyzcoder.kassa01.ui.items.util.ItemsRecyclerViewAdapter
import kg.kyrgyzcoder.kassa01.ui.items.viewmodel.ItemViewModel
import kg.kyrgyzcoder.kassa01.ui.items.viewmodel.ItemViewModelFactory
import kg.kyrgyzcoder.kassa01.ui.login.CashierLoginActivity
import kg.kyrgyzcoder.kassa01.ui.sell.util.Cat2RecyclerViewAdapter
import kg.kyrgyzcoder.kassa01.ui.sell.util.CustomDelete
import kg.kyrgyzcoder.kassa01.ui.sell.util.CustomSearchBarcode
import kg.kyrgyzcoder.kassa01.ui.sell.util.CustomSignOut
import kg.kyrgyzcoder.kassa01.util.*
import kotlinx.coroutines.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.Exception


class SellMainFragment : Fragment(), KodeinAware, ItemListener,
    SellRecyclerViewAdapter.SellCheckoutClickListener, ItemsRecyclerViewAdapter.ItemsClickListener,
    CustomDelete.CustomDeleteListener, CustomSearchBarcode.CustomSearchBarcodeListener,
    Cat2RecyclerViewAdapter.Cat2ClickListener, CustomSignOut.CustomSignOutListener {

    override val kodein: Kodein by closestKodein()
    private val itemViewModelFactory: ItemViewModelFactory by instance()

    private lateinit var itemViewModel: ItemViewModel

    private var _binding: FragmentSellMainBinding? = null
    private val binding: FragmentSellMainBinding get() = _binding!!

    private lateinit var cameraSource: CameraSource
    private lateinit var detector: LimitToOneBarcodeDetector
    private var adapter: SellRecyclerViewAdapter? = null
    private lateinit var mp: MediaPlayer

    private var checkoutList = mutableListOf<ModelActive>()

    private lateinit var adapterItem: ItemsRecyclerViewAdapter
    private val items = mutableListOf<ModelActive>()
    private var pageI: Int = 1

    private lateinit var adapterCat2: Cat2RecyclerViewAdapter
    private var categories = mutableListOf<ModelCategory>()
    private var pageCat2: Int = 1

    private var category: String = ""
    private var totalCost: Float = 0f

    private var sellItemsList = mutableListOf<ModelSellItem>()

    private var codeList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemViewModel = ViewModelProvider(
            requireActivity(),
            itemViewModelFactory
        ).get(ItemViewModel::class.java)

        itemViewModel.setListener(this)
        itemViewModel.getUserType()

        binding.buttonNext.setOnClickListener {
            if (sellItemsList.isNotEmpty())
                handleNext()
        }
    }

    private fun handleNext() {
        val modelPostTransaction = ModelPostTransaction(sellItemsList, 0)
        binding.progressBar.show()
        itemViewModel.postNewTransAction(modelPostTransaction)
    }

    override fun setReceiptSuccess(html: String) {
        codeList.clear()
        sellItemsList.clear()
        checkoutList.clear()
        binding.textViewTotalBuyFloat.text = getString(R.string._0_c)
        adapter?.submitList(checkoutList)
        adapter?.notifyDataSetChanged()
        binding.progressBar.hide()
        val intent = Intent(requireContext(), ReceiptActivity::class.java)
        intent.putExtra(EXTRA_RECEIPT, html)
        startActivity(intent)
    }

    override fun getReceiptFail(code: Int?) {
        requireActivity().toast("Error send transaction: $code")
        binding.progressBar.hide()
    }

    override fun setUserType(userType: Int) {
        if (userType == 2) {
            handleCashier()
        } else {
            binding.sellView.hide()
            binding.adminView.show()
            binding.buttonSignOut.setOnClickListener {
                val dialog = CustomSignOut(getString(R.string.signOutAdminText), this)
                dialog.show(requireActivity().supportFragmentManager, "SignOutAdmin")
            }
        }
    }

    override fun onSignOutConfirm() {
        itemViewModel.adminSignOut()
    }

    override fun adminSignedOut() {
        val intent = Intent(requireContext(), CashierLoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        requireActivity().finish()
        startActivity(intent)
    }

    private fun handleCashier() {
        itemViewModel.getItem(pageI, category)
        itemViewModel.getCategories(pageCat2)

        binding.swipeRefresh.setOnRefreshListener {
            pageI = 1
            itemViewModel.getItem(pageI, category)
        }

        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                pageI++
                itemViewModel.getItem(pageI, category)
            }
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askCameraPermission()
        } else {
            setupControls()
        }

        binding.showHideItems.setOnClickListener {
            if (binding.swipeRefresh.visibility == View.VISIBLE) {
                binding.swipeRefresh.visibility = View.GONE
                binding.imgViewArrow.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.arr_down
                    )
                )
                binding.textViewHideShow.text = getString(R.string.showList)
            } else {
                binding.swipeRefresh.visibility = View.VISIBLE
                binding.imgViewArrow.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.arr_up
                    )
                )
                binding.textViewHideShow.text = getString(R.string.hideList)
            }
        }

        binding.typeUidButton.setOnClickListener {
            val dialog = CustomSearchBarcode(this)
            dialog.show(requireActivity().supportFragmentManager, "Search by barcode")
        }
    }

    override fun onSearchBarcode(barcode: String) {
        itemViewModel.getItemByUid(barcode)
    }

    override fun setItems(itemPag: ModelActivePag) {
        if (pageI == 1)
            items.clear()
        items.addAll(itemPag.results)
        binding.swipeRefresh.isRefreshing = false
        binding.recyclerViewItems.setHasFixedSize(true)
        adapterItem = ItemsRecyclerViewAdapter(this)
        binding.recyclerViewItems.adapter = adapterItem
        adapterItem.submitList(items)
    }


    override fun getItemFail(code: Int?) {
        requireActivity().toast("Get Item Fail: code: $code")
        binding.swipeRefresh.isRefreshing = false
    }

    override fun setCategories(cats: ModelCategoryPag) {
        if (pageCat2 == 1)
            categories.clear()
        categories.add(ModelCategory(0, 0F, 0F, "Все продукции", 0))
        categories.addAll(cats.results)
        binding.recyclerViewFilter.setHasFixedSize(true)
        adapterCat2 = Cat2RecyclerViewAdapter(this)
        binding.recyclerViewFilter.adapter = adapterCat2
        adapterCat2.submitList(categories)
    }

    override fun onCategoryClick(catId: Int, catName: String) {
        category = if (catId == 0)
            ""
        else
            catId.toString()
        pageI = 1
        binding.titleLabel.text = catName
        itemViewModel.getItem(pageI, category)
    }

    override fun getCategoryFail(code: Int?) {
        requireActivity().toast("Get Categories Fail: code: $code")
    }

    private fun setupControls() {
        adapter = SellRecyclerViewAdapter(this)
        binding.recyclerViewResult.setHasFixedSize(true)
        binding.recyclerViewResult.adapter = adapter
        adapter?.submitList(checkoutList)
        detector = LimitToOneBarcodeDetector(requireContext())
        cameraSource = CameraSource.Builder(requireActivity(), detector)
            .setAutoFocusEnabled(true)
            .build()
        binding.cameraSurfaceView.holder.addCallback(surfaceCallback)
        detector.setProcessor(processor)
        mp = MediaPlayer.create(requireContext(), R.raw.sound)
    }

    private fun askCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
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
                if (!codeList.contains(code.displayValue))
                    itemViewModel.getItemByUid(code.displayValue)
                codeList.add(code.displayValue)
            } else {
            }
        }
    }

    override fun setItemByUid(modelActivePag: ModelActivePag) {
        if (!modelActivePag.results.isNullOrEmpty()) {
            val modelActive = modelActivePag.results[0]
            var i = 0
            var exists = false
            for (item in sellItemsList) {
                if (item.activeitem == modelActive.id) {
                    exists = true
                    break
                }
                i++
            }

            if (!exists) {
                mp.start()
                sellItemsList.add(ModelSellItem(modelActive.id, 1))
                checkoutList.add(modelActive)
                totalCost += modelActive.cost
                val text = "${this.totalCost} c."
                binding.textViewTotalBuyFloat.text = text
                updateList()
            } else {
                Log.d("DADADADADA", "setItemByUid: exists")
            }
        } else {
            requireActivity().toast("Item with this barcode not found!!!")
        }
    }

    override fun setItemByUidFail(code: Int?) {
        requireActivity().toast("Failed to get by uid: $code")
    }

    //TODO check adapter null
    @SuppressLint("MissingPermission")
    private fun updateList() {
        adapter?.submitList(checkoutList)
        adapter?.notifyDataSetChanged()

    }

    override fun addQuantity(itemId: Int, totalCost: Float) {
        var i = 0
        for (item in sellItemsList) {
            if (item.activeitem == itemId) {
                sellItemsList[i].quantity++
                break
            }
            i++
        }
        this.totalCost += totalCost
        val text = "${this.totalCost} c."
        binding.textViewTotalBuyFloat.text = text
    }

    override fun subtractQuantity(itemId: Int, isDeleting: Boolean, totalCost: Float) {
        if (isDeleting) {
            val dialog = CustomDelete(getString(R.string.deleteFromList), totalCost, itemId, this)
            dialog.show(requireActivity().supportFragmentManager, "DeleteList")
        } else {
            var i = 0
            for (item in sellItemsList) {
                if (item.activeitem == itemId) {
                    sellItemsList[i].quantity--
                    break
                }
                i++
            }
            this.totalCost -= totalCost
            val text = "${this.totalCost} c."
            binding.textViewTotalBuyFloat.text = text
        }
    }

    override fun onDeleteConfirmed(cost: Float, itemId: Int) {
        var i = 0
        var code = ""
        for (item in checkoutList) {
            if (item.id == itemId) {
                code = item.itemglobal.uniqueid
                checkoutList.removeAt(i)
                sellItemsList.removeAt(i)
                updateList()
                break
            }
            i++
        }
        this.totalCost -= cost
        val text = "${this.totalCost} c."
        binding.textViewTotalBuyFloat.text = text

        codeList.remove(code)
    }

    override fun onItemClick(position: Int) {
        val item = adapterItem.getItemAtPos(position)
        var exists = false
        for (i in checkoutList) {
            if (i.id == item.id) {
                exists = true
                break
            }
        }
        if (!exists) {
            checkoutList.add(item)
            sellItemsList.add(ModelSellItem(item.id, 1))
            totalCost += item.cost
            val text = "$totalCost c."
            binding.textViewTotalBuyFloat.text = text
            updateList()
        }
    }

    private val surfaceCallback = object : SurfaceHolder.Callback {
        @SuppressLint("MissingPermission")
        override fun surfaceCreated(holder: SurfaceHolder) {
            try {
                cameraSource.start(holder)
            } catch (e: Exception) {
                requireActivity().toast("Error : ${e.message}")
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
                requireActivity().toast("Please allow camera usage")
            }
        }
    }

    override fun createCategorySuccess() {
    }

    override fun createCategoryFail(code: Int?) {
    }

    override fun postItemSuccess(status: Boolean) {
    }

    override fun postItemFail(code: Int?) {
    }


}

//private fun updateUIAdapter(i: Int) {
//    val current = adapter.getItemAtPos(i)
//
//    try {
//        val viewHolder = binding.recyclerViewResult.findViewHolderForAdapterPosition(i) as SellRecyclerViewAdapter.ViewHolderSell?
//        if (viewHolder != null) {
//            val count = viewHolder.binding.countTextView.text.toString().toInt()
//            val cost = current.cost.toString() + " с. x $count"
//            viewHolder.binding.itemCost.text = cost
//            val t = count.toFloat() * current.cost
//            val totalCost = "$t c."
//            viewHolder.binding.itemTotalCost.text = totalCost
//        } else {
//            Log.d("DADADADADA12", "updateUIAdapter: null viewholder $i current: $current")
//        }
//
//    } catch (e: NullPointerException) {
//        Log.d("DADADADADA", "updateUIAdapter: null viewholder $i current: $current")
//    }
//
//}