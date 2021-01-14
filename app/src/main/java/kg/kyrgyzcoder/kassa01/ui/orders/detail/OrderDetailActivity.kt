package kg.kyrgyzcoder.kassa01.ui.orders.detail

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.data.model.ModelOrderFake
import kg.kyrgyzcoder.kassa01.data.network.order.model.ModelFbOrder
import kg.kyrgyzcoder.kassa01.data.network.order.model.ModelItemFb
import kg.kyrgyzcoder.kassa01.data.network.order.model.ModelOrderDel
import kg.kyrgyzcoder.kassa01.databinding.ActivityOrderDetailBinding
import kg.kyrgyzcoder.kassa01.ui.orders.utils.*
import kg.kyrgyzcoder.kassa01.ui.orders.viewmodel.OrderViewModel
import kg.kyrgyzcoder.kassa01.ui.orders.viewmodel.OrderViewModelFactory
import kg.kyrgyzcoder.kassa01.ui.reports.util.ItemIncomeRecyclerViewAdapter
import kg.kyrgyzcoder.kassa01.util.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailActivity : AppCompatActivity(), KodeinAware, OrdersListener,
    CustomConfirmOrder.CustomConfirmOrderListener, RecyclerViewOrderItems.ItemOderClickListener,
    CustomCallUser.CustomCallUserListener, CustomAddressConfirm.CustomAddressConfirmListener,
    CustomAskForDelivery.CustomAskForDeliveryListener {

    override val kodein: Kodein by closestKodein()
    private val ordersViewModelFactory: OrderViewModelFactory by instance()

    private lateinit var ordersViewModel: OrderViewModel

    private lateinit var binding: ActivityOrderDetailBinding

    private val items = mutableListOf<Int>()

    private lateinit var adapter: RecyclerViewOrderItems

    private var ref = ""

    private var userPhoneNumber: String = ""
    private var userAddress: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.parseColor("#f3f4f6")

        ordersViewModel =
            ViewModelProvider(this, ordersViewModelFactory).get(OrderViewModel::class.java)
        ordersViewModel.setListener(this)
        ordersViewModel.getUserPhoneAddress()

        binding.arrBackOrderDetail.setOnClickListener {
            onBackPressed()
        }

        val order = intent.getParcelableExtra<ModelFbOrder>(EXTRA_ORDER_MODEl) as ModelFbOrder
        ref = intent.getStringExtra(EXTRA_ORDER_REF)!!
        val type = intent.getStringExtra(EXTRA_ORDER_TYPE)

        initRecyclerView()
        initUI(order, type ?: "")

        FirebaseFirestore.getInstance().document(ref).collection("items").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val docs = it.result.documents
                    for (doc in docs) {
                        items.add(doc.getLong("id")!!.toInt())
                    }
                    binding.prBar.hide()
                }
            }

        binding.buttonConfirmOrder.setOnClickListener {
            val dialog = CustomConfirmOrder(order, this)
            dialog.show(supportFragmentManager, "ConfirmOrder")
        }
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val db = FirebaseFirestore.getInstance()
        val query = db.document(ref).collection("items")
        val options: FirestoreRecyclerOptions<ModelItemFb> =
            FirestoreRecyclerOptions.Builder<ModelItemFb>()
                .setQuery(query, ModelItemFb::class.java)
                .build()
        binding.prBar.hide()
        adapter = RecyclerViewOrderItems(options, this)
        binding.recyclerView.adapter = adapter
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun initUI(order: ModelFbOrder, type: String) {
        val clientName = if (order.clientName.isNullOrEmpty())
            getString(R.string.notRegistered)
        else
            order.clientName
        val text = "Заказ от $clientName"

        binding.toolbar.text = text

        binding.dateTextView.text = getDateToShow(order.dateOrder.toDate())

        binding.phoneTextView.text = order.clientPhone
        binding.addressTextView.text = order.clientAddress

        val t2 = "${order.totalCost} с"
        binding.totalTextView.text = t2

        Log.d("NURIKO", "initUI: ${order.orderType}")

        binding.typeTextView.text = type

        binding.paymentTextView.text = if (order.isPaid) {
            binding.paymentTextView.setTextColor(Color.parseColor("#0A710E"))
            getString(R.string.paid)
        } else {
            binding.paymentTextView.setTextColor(Color.parseColor("#FAA72E"))
            getString(R.string.cash)
        }

        if (order.deliveryCalled()) {
            binding.buttonConfirmOrder.visibility = View.GONE
            binding.cancelButton.visibility = View.GONE
            binding.buttonCallDelivery.visibility = View.GONE
        } else if (order.status == 2 && !order.deliveryCalled()) {
            binding.buttonConfirmOrder.visibility = View.GONE
            binding.cancelButton.visibility = View.GONE
            binding.buttonCallDelivery.visibility = View.VISIBLE
        } else {
            binding.buttonConfirmOrder.visibility = View.VISIBLE
            binding.cancelButton.visibility = View.VISIBLE
            binding.buttonCallDelivery.visibility = View.GONE
        }

        binding.phoneTextView.setOnClickListener {
            callUser(order.clientPhone)
        }
        binding.fabCallClient.setOnClickListener {
            callUser(order.clientPhone)
        }

    }

    private fun callUser(clientPhone: String?) {
        val dialog = CustomCallUser(clientPhone, this)
        dialog.show(supportFragmentManager, "CallUser")
    }

    override fun callUserConfirmed(phoneNumber: String?) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)
    }

    private fun getDateToShow(date: Date): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT)
        val dateTimeStr = sdf.format(date)
        val timeStr = dateTimeStr.takeLast(5)
        val dateStr = getDateToday(this, dateTimeStr.take(10), 1)
        return "$dateStr $timeStr"
    }

    override fun onItemClick(position: Int, itemId: Int) {

    }

    override fun setUserAddressPhone(address: String?, phone: String?) {
        this.userAddress = address ?: ""
        this.userPhoneNumber = phone ?: ""
    }

    override fun orderConfirmed(order: ModelFbOrder) {
        binding.prBar.show()
        binding.prBar.hide()
        val map = mutableMapOf<String, Any>()
        map["status"] = 2
        FirebaseFirestore.getInstance().document(ref).set(map, SetOptions.merge())
        toast(getString(R.string.orderConfirmed))
        binding.buttonConfirmOrder.visibility = View.GONE
        binding.cancelButton.visibility = View.GONE
        binding.buttonCallDelivery.visibility = View.VISIBLE

        binding.buttonCallDelivery.setOnClickListener {
            val dialog = CustomAddressConfirm(userAddress, userPhoneNumber, order, this)
            dialog.show(supportFragmentManager, "addressConfirm")
        }

        Log.d("NURIKO", "orderConfirmed: type:")

        if (order.orderType == 1) {
            val dialog = CustomAskForDelivery(order, this)
            dialog.show(supportFragmentManager, "askForDelivery")
        } else {
            onBackPressed()
        }
    }

    override fun callDeliveryConfirmed(order: ModelFbOrder) {
        val dialog = CustomAddressConfirm(userAddress, userPhoneNumber, order, this)
        dialog.show(supportFragmentManager, "addressConfirm")
    }

    override fun deliveryNotNeeded() {
    }

    override fun onChangeClick(address: String, phone: String, order: ModelFbOrder) {
        val model = ModelOrderDel(
            address_to = order.clientAddress,
            receiver_name = order.clientName,
            receiver_phone = order.clientPhone,
            partner_order_id = order.clientId * 100,
            address_from = address,
            customer_phone = phone,
            customer_amount = order.totalCost.toInt(),
            pays_receiver = !order.isPaid
        )
        ordersViewModel.callDelivery(model)
    }

    override fun deliveryCallSuccess() {
        toast(getString(R.string.deliverySuccess))
        val map = mutableMapOf<String, Any>()
        map["deliveryCalled"] = true
        FirebaseFirestore.getInstance().document(ref).set(map, SetOptions.merge())
        onBackPressed()
    }

    override fun deliveryCallFail(code: Int?) {
        toast(getString(R.string.deliveryCallFail))
    }
}