package kg.kyrgyzcoder.kassa01.ui.items

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.data.network.item.model.*
import kg.kyrgyzcoder.kassa01.data.network.sell.model.ModelTransactionResponse
import kg.kyrgyzcoder.kassa01.databinding.FragmentItemsMainBinding
import kg.kyrgyzcoder.kassa01.ui.items.util.CategoriesRecyclerViewAdapter
import kg.kyrgyzcoder.kassa01.ui.items.util.CustomAddCat
import kg.kyrgyzcoder.kassa01.ui.items.util.ItemListener
import kg.kyrgyzcoder.kassa01.ui.items.viewmodel.ItemViewModel
import kg.kyrgyzcoder.kassa01.ui.items.viewmodel.ItemViewModelFactory
import kg.kyrgyzcoder.kassa01.util.EXTRA_CATEGORY_ITEM
import kg.kyrgyzcoder.kassa01.util.hide
import kg.kyrgyzcoder.kassa01.util.show
import kg.kyrgyzcoder.kassa01.util.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class ItemsMainFragment : Fragment(), CustomAddCat.CustomAddCatListener, KodeinAware, ItemListener,
    CategoriesRecyclerViewAdapter.CategoryClickListener {

    override val kodein: Kodein by closestKodein()
    private val itemViewModelFactory: ItemViewModelFactory by instance()

    private lateinit var itemViewModel: ItemViewModel

    private var _binding: FragmentItemsMainBinding? = null
    private val binding: FragmentItemsMainBinding get() = _binding!!

    private lateinit var adapter: CategoriesRecyclerViewAdapter

    private var page: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemsMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemViewModel = ViewModelProvider(
            requireActivity(),
            itemViewModelFactory
        ).get(ItemViewModel::class.java)

        itemViewModel.setListener(this)

        itemViewModel.getCategories(page)
        binding.progressBar.show()

        binding.swipeRefresh.setOnRefreshListener {
            page = 1
            itemViewModel.getCategories(page)
        }

        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                binding.progressBar.show()
                page++
                itemViewModel.getCategories(page)
            }
        }

        binding.fabAddFolder.setOnClickListener {
            val dialog = CustomAddCat(this)
            dialog.show(requireActivity().supportFragmentManager, "Add Cat")
        }
    }

    override fun onResume() {
        super.onResume()
        itemViewModel.setListener(this)
        itemViewModel.getCategories(page)
    }

    override fun setCategories(cats: ModelCategoryPag) {
        binding.progressBar.hide()
        binding.swipeRefresh.isRefreshing = false
        Log.d("NURIKO", "setCategories: OK")
        adapter = CategoriesRecyclerViewAdapter(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
        adapter.submitList(cats.results)
    }

    override fun getCategoryFail(code: Int?) {
        binding.progressBar.hide()
        binding.swipeRefresh.isRefreshing = false
        requireActivity().toast(getString(R.string.errorTryAgain))
    }

    override fun onCategoryClick(position: Int) {
        val current = adapter.getItemAtPos(position)
        val intent = Intent(requireContext(), ItemsActivity::class.java)
        intent.putExtra(EXTRA_CATEGORY_ITEM, current)
        startActivity(intent)
    }

    override fun createNewFolder(name: String) {
        requireActivity().toast("Create folder $name")
        binding.progressBar.show()
        binding.swipeRefresh.isRefreshing = false
        val model = ModelCreateCategory(
            0F, 0F, name, 0
        )
        itemViewModel.createNewCategory(model)
    }

    override fun createCategorySuccess() {
        page = 1
        itemViewModel.getCategories(page)
        binding.progressBar.show()
    }

    override fun createCategoryFail(code: Int?) {
        binding.progressBar.hide()
        requireActivity().toast(getString(R.string.errorTryAgain))
    }

    override fun postItemSuccess(status: Boolean) {

    }

    override fun postItemFail(code: Int?) {
    }

    override fun setItems(itemPag: ModelActivePag) {
    }

    override fun getItemFail(code: Int?) {
    }

    override fun setItemByUid(modelActivePag: ModelActivePag) {

    }

    override fun setItemByUidFail(code: Int?) {
    }

    override fun setUserType(userType: Int) {

    }

    override fun adminSignedOut() {
    }

    override fun setReceiptSuccess(modelTransactionResponse: ModelTransactionResponse) {

    }

    override fun getReceiptFail(code: Int?) {
    }
}