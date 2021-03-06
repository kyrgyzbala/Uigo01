package kg.kyrgyzcoder.kassa01.ui.items

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.kassa01.data.network.item.model.ModelActive
import kg.kyrgyzcoder.kassa01.databinding.ActivityItemEditBinding
import kg.kyrgyzcoder.kassa01.ui.items.util.EditItemListener
import kg.kyrgyzcoder.kassa01.ui.items.viewmodel.ItemViewModel
import kg.kyrgyzcoder.kassa01.ui.items.viewmodel.ItemViewModelFactory
import kg.kyrgyzcoder.kassa01.util.EXTRA_ITEM
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class ItemEditActivity : AppCompatActivity(), KodeinAware, EditItemListener {

    override val kodein: Kodein by closestKodein()
    private val itemViewModelFactory: ItemViewModelFactory by instance()

    private lateinit var itemViewModel: ItemViewModel

    private lateinit var binding: ActivityItemEditBinding

    private var currentCost: Float = 0F
    private var editing = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE
        itemViewModel = ViewModelProvider(this, itemViewModelFactory).get(ItemViewModel::class.java)
        itemViewModel.setEditListener(this)

        val item = intent.getSerializableExtra(EXTRA_ITEM) as ModelActive
        initUI(item)

        addListeners()

        binding.saveButton.setOnClickListener {

        }
    }

    private fun addListeners() {
        binding.addCostEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.addCostEditText.text.toString()
                        .isNotEmpty() && editing == 1
                ) {
                    val addedCost = binding.addCostEditText.text.toString().toFloat()
                    binding.yourCostEditText.setText((addedCost + currentCost).toString())
                    val percent = (addedCost / currentCost) * 100
                    binding.addPercentEditText.setText(percent.toString())
                    Log.d("ItemEditActivity", "onTextChanged (line 58): $percent")
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.addPercentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.addPercentEditText.text.toString()
                        .isNotEmpty() && editing == 2
                ) {
                    val percent = binding.addPercentEditText.text.toString().toFloat()
                    val addedCost = currentCost * (percent / 100)
                    val yourCost = currentCost + addedCost
                    binding.addCostEditText.setText(addedCost.toString())
                    binding.yourCostEditText.setText(yourCost.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.yourCostEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.yourCostEditText.text.toString()
                        .isNotEmpty() && editing == 3
                ) {
                    val yourCost = binding.yourCostEditText.text.toString().toFloat()
                    val addedCost = yourCost - currentCost
                    val percent = (addedCost / yourCost) * 100

                    binding.addPercentEditText.setText(percent.toString())
                    binding.addCostEditText.setText(addedCost.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.addCostEditText.setOnFocusChangeListener { view, b ->
            if (b)
                editing = 1
        }
        binding.addPercentEditText.setOnFocusChangeListener { view, b ->
            if (b)
                editing = 2
        }
        binding.yourCostEditText.setOnFocusChangeListener { view, b ->
            if (b)
                editing = 3
        }
    }

    private fun initUI(item: ModelActive) {
        this.currentCost = item.cost
        binding.toolbar.title = item.itemglobal.name
        binding.itemCostTextView.text = item.cost.toString()
        binding.yourCostEditText.setText(item.cost.toString())
    }
}