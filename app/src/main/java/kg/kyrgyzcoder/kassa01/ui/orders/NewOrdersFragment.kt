package kg.kyrgyzcoder.kassa01.ui.orders

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.data.network.order.model.ModelFbOrder
import kg.kyrgyzcoder.kassa01.databinding.FragmentNewOrdersBinding
import kg.kyrgyzcoder.kassa01.ui.orders.detail.OrderDetailActivity
import kg.kyrgyzcoder.kassa01.ui.orders.utils.NewOrderRecyclerViewAdapter
import kg.kyrgyzcoder.kassa01.util.*


class NewOrdersFragment : Fragment(), NewOrderRecyclerViewAdapter.OrderClickListener {

    private var _binding: FragmentNewOrdersBinding? = null
    private val binding: FragmentNewOrdersBinding get() = _binding!!

    private var adapter: NewOrderRecyclerViewAdapter? = null

    private lateinit var mp: MediaPlayer

    private var firstLoadDone: Boolean = false
    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mp = MediaPlayer.create(requireContext(), R.raw.iphone)
        userId = requireActivity().intent.getIntExtra(EXTRA_USER_ID, 0)

        initRecyclerView(userId)

        binding.swipeRefresh.setOnRefreshListener {
            firstLoadDone = false
            initRecyclerView(userId)
        }

    }

    override fun onResume() {
        super.onResume()
        initRecyclerView(userId)
    }

    private fun initRecyclerView(userId: Int) {

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("orders").whereEqualTo("storeId", 2)
            .whereEqualTo("status", 1)
            .orderBy("dateOrder", Query.Direction.DESCENDING)

        val options: FirestoreRecyclerOptions<ModelFbOrder> =
            FirestoreRecyclerOptions.Builder<ModelFbOrder>()
                .setQuery(query, ModelFbOrder::class.java)
                .build()
        binding.prBar.hide()
        binding.swipeRefresh.isRefreshing = false
        adapter = NewOrderRecyclerViewAdapter(options, this)
        binding.recyclerView.adapter = adapter
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    override fun onOrderClick(position: Int, model: ModelFbOrder, type: String) {
        if (adapter != null) {
            val ref = adapter!!.snapshots.getSnapshot(position).reference.path
            val intent = Intent(requireContext(), OrderDetailActivity::class.java)
            intent.putExtra(EXTRA_ORDER_REF, ref)
            intent.putExtra(EXTRA_ORDER_MODEl, model)
            intent.putExtra(EXTRA_ORDER_TYPE, type)
            startActivity(intent)
        }
    }

}