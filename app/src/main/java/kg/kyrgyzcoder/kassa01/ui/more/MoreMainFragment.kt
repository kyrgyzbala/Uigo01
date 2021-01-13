package kg.kyrgyzcoder.kassa01.ui.more

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.databinding.FragmentMoreMainBinding
import kg.kyrgyzcoder.kassa01.ui.login.CashierLoginActivity
import kg.kyrgyzcoder.kassa01.ui.more.util.CustomCloseDay
import kg.kyrgyzcoder.kassa01.ui.more.util.ModelUserCash
import kg.kyrgyzcoder.kassa01.ui.more.util.MoreListener
import kg.kyrgyzcoder.kassa01.ui.more.viewmodel.MoreViewModel
import kg.kyrgyzcoder.kassa01.ui.more.viewmodel.MoreViewModelFactory
import kg.kyrgyzcoder.kassa01.ui.sell.util.CustomSignOut
import kg.kyrgyzcoder.kassa01.util.EXTRA_ADD_TYPE
import kg.kyrgyzcoder.kassa01.util.getDateToday
import kg.kyrgyzcoder.kassa01.util.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class MoreMainFragment : Fragment(), KodeinAware, MoreListener,
    CustomSignOut.CustomSignOutListener, CustomCloseDay.CustomDayCloseListener {

    override val kodein: Kodein by closestKodein()
    private val moreViewModelFactory: MoreViewModelFactory by instance()

    private lateinit var moreViewModel: MoreViewModel

    private var _binding: FragmentMoreMainBinding? = null
    private val binding: FragmentMoreMainBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moreViewModel = ViewModelProvider(
            requireActivity(), moreViewModelFactory
        ).get(MoreViewModel::class.java)
        moreViewModel.setMoreListener(this)

        moreViewModel.getUserData()

    }

    override fun setUserData(modelUserCash: ModelUserCash) {
        binding.cashierUserNameTextView.text = modelUserCash.userFullname
        binding.cashierPhoneTextView.text = modelUserCash.userUsername
        if (!modelUserCash.lastSignIn.isNullOrEmpty()) {
            val date = getDateToday(requireContext(), modelUserCash.lastSignIn.take(10), 2)
            val temp = modelUserCash.lastSignIn.take(16)
            val time = temp.takeLast(5)
            val timeToShow = "$date в $time"
            binding.startTimeTextView.text = timeToShow
        }
        if (modelUserCash.userType == 1) {
            binding.closeTheDayButton.visibility = View.GONE
            handleAdmin()
        } else {
            binding.addNewPackerButton.visibility = View.GONE
            binding.addNewCashierButton.visibility = View.GONE
            binding.adminSignOutButton.visibility = View.GONE
            handleCashier()
        }

    }

    private fun handleCashier() {
        binding.closeTheDayButton.setOnClickListener {
            val dialog = CustomCloseDay(this)
            dialog.show(requireActivity().supportFragmentManager, "CloseDay")
        }
    }

    override fun closeDayConfirmed() {
        moreViewModel.closeTheDay()
    }

    override fun dayCloseSuccess() {
        val intent = Intent(requireContext(), CashierLoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        requireActivity().finish()
        startActivity(intent)
    }

    override fun dayCloseFail(code: Int?) {
        requireActivity().toast("Day Close FAILED")
    }

    override fun cashierIdNull() {
        requireActivity().toast("Cashier ID == NULL")
    }

    private fun handleAdmin() {
        binding.addNewPackerButton.setOnClickListener {
            val intent = Intent(requireContext(), AddNewEmplActivity::class.java)
            intent.putExtra(EXTRA_ADD_TYPE, 3)
            startActivity(intent)
        }

        binding.addNewCashierButton.setOnClickListener {
            val intent = Intent(requireContext(), AddNewEmplActivity::class.java)
            intent.putExtra(EXTRA_ADD_TYPE, 2)
            startActivity(intent)
        }

        binding.adminSignOutButton.setOnClickListener {
            val dialog = CustomSignOut(getString(R.string.signOutAdminText), this)
            dialog.show(requireActivity().supportFragmentManager, "SignOutAdmin")
        }
    }

    override fun onSignOutConfirm() {
        moreViewModel.adminSignOut()
    }

    override fun signedOutAdmin() {
        val intent = Intent(requireContext(), CashierLoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        requireActivity().finish()
        startActivity(intent)
    }
}