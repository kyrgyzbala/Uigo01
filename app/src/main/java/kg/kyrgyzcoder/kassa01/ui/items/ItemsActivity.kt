package kg.kyrgyzcoder.kassa01.ui.items

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.kassa01.data.network.item.model.*
import kg.kyrgyzcoder.kassa01.data.network.sell.model.ModelTransactionResponse
import kg.kyrgyzcoder.kassa01.databinding.ActivityItemsBinding
import kg.kyrgyzcoder.kassa01.ui.items.util.*
import kg.kyrgyzcoder.kassa01.ui.items.viewmodel.ItemViewModel
import kg.kyrgyzcoder.kassa01.ui.items.viewmodel.ItemViewModelFactory
import kg.kyrgyzcoder.kassa01.util.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class ItemsActivity : AppCompatActivity(), KodeinAware, CustomChooseAdd.CustomChooseAddListener,
    CustomAddManually.CustomAddManualListener, ItemListener,
    ItemsRecyclerViewAdapter.ItemsClickListener {

    override val kodein: Kodein by closestKodein()
    private val itemViewModelFactory: ItemViewModelFactory by instance()

    private lateinit var itemViewModel: ItemViewModel

    private lateinit var binding: ActivityItemsBinding

    private var category: ModelCategory? = null

    private var items = mutableListOf<ModelActive>()

    private lateinit var adapter: ItemsRecyclerViewAdapter
    private var page: Int = 1

    private var userType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#f3f3f4")
        category = intent.getSerializableExtra(EXTRA_CATEGORY_ITEM) as ModelCategory

        itemViewModel = ViewModelProvider(this, itemViewModelFactory).get(ItemViewModel::class.java)
        itemViewModel.setListener(this)
        itemViewModel.getUserType()

        binding.arrBackItems.setOnClickListener {
            onBackPressed()
        }

        itemViewModel.getItem(page, category!!.id.toString())

        binding.swipeRefresh.setOnRefreshListener {
            page = 1
            itemViewModel.getItem(page, category!!.id.toString())
        }

        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                binding.prBar.show()
                page++
                itemViewModel.getItem(page, category!!.id.toString())
            }
        }

        binding.toolbar.title = category?.name

        binding.fabAddItem.setOnClickListener {
            val dialog = CustomChooseAdd(this)
            dialog.show(supportFragmentManager, "AddItem")
        }
    }

    override fun addWithScan() {
        val intent = Intent(this, ScanItemActivity::class.java)
        intent.putExtra(EXTRA_CATEGORY_ITEM, category)
        startActivity(intent)
    }

    override fun addManually() {
        val dialog = CustomAddManually(this)
        dialog.show(supportFragmentManager, "AddManually")
    }

    override fun addNewItem(itemCode: String, itemCost: Float, itemCount: Int) {
        toast("code: $itemCode, cost: $itemCost, count: $itemCount, category: ${category?.name}")
        Log.d(
            "NURR",
            "confirmAddItem: code: $itemCode, cost: $itemCost, count: $itemCount, category: ${category?.name}"
        )
        val modelPostItem = ModelPostItem(itemCode, itemCost, category!!.id, itemCount, 0)
        itemViewModel.postNewItem(modelPostItem)
        binding.prBar.show()
    }

    override fun postItemSuccess(status: Boolean) {
        binding.prBar.hide()
        toast("Item added success: $status")
    }

    override fun postItemFail(code: Int?) {
        binding.prBar.hide()
        toast("Item add fail: $code")
    }

    override fun setItems(itemPag: ModelActivePag) {
        if (page == 1)
            items.clear()
        items.addAll(itemPag.results)
        adapter = ItemsRecyclerViewAdapter(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
        binding.swipeRefresh.isRefreshing = false
        binding.prBar.hide()
        adapter.submitList(items)
    }

    override fun getItemFail(code: Int?) {
        if (page == 1)
            toast("Get Fail: $code")
        binding.swipeRefresh.isRefreshing = false
        binding.prBar.hide()
    }

    override fun setItemByUid(modelActivePag: ModelActivePag) {

    }

    override fun setItemByUidFail(code: Int?) {
    }

    override fun setUserType(userType: Int) {
        this.userType = userType
        if (userType == 1) {
            binding.fabAddItem.visibility = View.VISIBLE
        } else {
            binding.fabAddItem.visibility = View.GONE
        }
    }

    override fun adminSignedOut() {

    }

    override fun setReceiptSuccess(modelTransactionResponse: ModelTransactionResponse) {

    }

    override fun getReceiptFail(code: Int?) {
    }

    override fun createCategorySuccess() {

    }

    override fun createCategoryFail(code: Int?) {
    }

    override fun setCategories(cats: ModelCategoryPag) {
    }

    override fun getCategoryFail(code: Int?) {
    }

    override fun onItemClick(position: Int) {
        if (userType == 1) {
            val model = adapter.getItemAtPos(position)
            val intent = Intent(this, ItemEditActivity::class.java)
            intent.putExtra(EXTRA_ITEM, model)
            startActivity(intent)
        } else {
            toast("You do not have access to edit item")
        }
    }


}