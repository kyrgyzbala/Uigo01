package kg.kyrgyzcoder.kassa01.ui.reports

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import kg.kyrgyzcoder.kassa01.R
import kg.kyrgyzcoder.kassa01.databinding.FragmentReportsMainBinding
import kg.kyrgyzcoder.kassa01.ui.reports.util.ReportsPagerAdapter
import kg.kyrgyzcoder.kassa01.util.getDateToday
import java.util.*


class ReportsMainFragment : Fragment() {

    private var _binding: FragmentReportsMainBinding? = null
    private val binding: FragmentReportsMainBinding get() = _binding!!

    private var selectedDate1: String = ""
    private var selectedDate2: String = ""

    private var currentButton: Int = 1
    private var currentPeriod: Int = 1

    private lateinit var sectionPagerAdapter: ReportsPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReportsMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDates()
        sectionPagerAdapter = ReportsPagerAdapter(requireActivity())
        binding.viewPager.adapter = sectionPagerAdapter
        addListeners()
    }

    private fun initDates() {
        val calendar = Calendar.getInstance()
        val mYear = calendar.get(Calendar.YEAR)
        val mDay = calendar.get(Calendar.DAY_OF_MONTH)
        val mMonth = calendar.get(Calendar.MONTH)

        val day = if (mDay < 10)
            "0$mDay"
        else
            mDay.toString()
        val mon = if (mMonth < 10)
            "0$mMonth"
        else
            mMonth.toString()
        selectedDate1 = "$day.$mon.$mYear"
        selectedDate2 = selectedDate1
        val date = getDateToday(requireContext(), "$day.$mon.$mYear", 1)
        binding.textViewDate1.text = date
        binding.textViewDate2.text = date
    }

    private fun handleSaleButtonClick() {
        if (currentButton != 1) {
            currentButton = 1
            binding.buttonSale.setTextColor(Color.WHITE)
            binding.buttonSale.setBackgroundResource(R.drawable.back_button_week)

            binding.buttonIn.setTextColor(Color.BLACK)
            binding.buttonIn.setBackgroundResource(R.drawable.back_button_week_unsel)
        }
    }

    private fun handleInButtonClick() {
        if (currentButton != 2) {
            currentButton = 2
            binding.buttonIn.setTextColor(Color.WHITE)
            binding.buttonIn.setBackgroundResource(R.drawable.back_button_week)

            binding.buttonSale.setTextColor(Color.BLACK)
            binding.buttonSale.setBackgroundResource(R.drawable.back_button_week_unsel)
        }
    }

    private fun addListeners() {

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        handleSaleButtonClick()
                    }
                    else -> {
                        handleInButtonClick()
                    }
                }
            }
        })

        binding.textViewDate1.setOnClickListener {
            val mYear = selectedDate1.takeLast(4).toInt()
            val mDay = selectedDate1.take(2).toInt()
            val temp = selectedDate1.take(5)
            val mMonth = temp.takeLast(2).toInt()
            val dialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val day = if (dayOfMonth < 10)
                        "0$dayOfMonth"
                    else
                        dayOfMonth.toString()
                    val mon = if (month < 10)
                        "0$month"
                    else
                        month.toString()
                    selectedDate1 = "$day.$mon.$year"
                    val date = getDateToday(requireContext(), "$day.$mon.$year", 1)
                    binding.textViewDate1.text = date
                },
                mYear,
                mMonth,
                mDay
            )
            dialog.show()
        }

        binding.textViewDate2.setOnClickListener {
            val mYear = selectedDate2.takeLast(4).toInt()
            val mDay = selectedDate2.take(2).toInt()
            val temp = selectedDate2.take(5)
            val mMonth = temp.takeLast(2).toInt()
            val dialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val day = if (dayOfMonth < 10)
                        "0$dayOfMonth"
                    else
                        dayOfMonth.toString()
                    val mon = if (month < 10)
                        "0$month"
                    else
                        month.toString()
                    selectedDate2 = "$day.$mon.$year"
                    val date = getDateToday(requireContext(), "$day.$mon.$year", 1)
                    binding.textViewDate2.text = date
                },
                mYear,
                mMonth,
                mDay
            )
            dialog.show()
        }

        binding.buttonSale.setOnClickListener {
            binding.viewPager.currentItem = 0
            handleSaleButtonClick()
        }

        binding.buttonIn.setOnClickListener {
            binding.viewPager.currentItem = 1
            handleInButtonClick()
        }

        binding.buttonToday.setOnClickListener {
            if (currentPeriod != 1) {
                unselectPeriod(currentPeriod)
                currentPeriod = 1
                selectPeriod()
            }
        }

        binding.buttonYest.setOnClickListener {
            if (currentPeriod != 2) {
                unselectPeriod(currentPeriod)
                currentPeriod = 2
                selectPeriod()
            }
        }

        binding.buttonWeek.setOnClickListener {
            if (currentPeriod != 3) {
                unselectPeriod(currentPeriod)
                currentPeriod = 3
                selectPeriod()
            }
        }

        binding.buttonMonth.setOnClickListener {
            if (currentPeriod != 4) {
                unselectPeriod(currentPeriod)
                currentPeriod = 4
                selectPeriod()
            }
        }

        binding.button6Months.setOnClickListener {
            if (currentPeriod != 5) {
                unselectPeriod(currentPeriod)
                currentPeriod = 5
                selectPeriod()
            }
        }

        binding.buttonYear.setOnClickListener {
            if (currentPeriod != 6) {
                unselectPeriod(currentPeriod)
                currentPeriod = 6
                selectPeriod()
            }
        }
    }

    private fun selectPeriod() {
        when (currentPeriod) {
            1 -> {
                binding.buttonToday.setTextColor(Color.WHITE)
                binding.buttonToday.setBackgroundResource(R.drawable.back_button_week)
            }
            2 -> {
                binding.buttonYest.setTextColor(Color.WHITE)
                binding.buttonYest.setBackgroundResource(R.drawable.back_button_week)
            }
            3 -> {
                binding.buttonWeek.setTextColor(Color.WHITE)
                binding.buttonWeek.setBackgroundResource(R.drawable.back_button_week)
            }
            4 -> {
                binding.buttonMonth.setTextColor(Color.WHITE)
                binding.buttonMonth.setBackgroundResource(R.drawable.back_button_week)
            }
            5 -> {
                binding.button6Months.setTextColor(Color.WHITE)
                binding.button6Months.setBackgroundResource(R.drawable.back_button_week)
            }
            else -> {
                binding.buttonYear.setTextColor(Color.WHITE)
                binding.buttonYear.setBackgroundResource(R.drawable.back_button_week)
            }
        }
    }

    private fun unselectPeriod(code: Int) {
        when (code) {
            1 -> {
                binding.buttonToday.setTextColor(Color.BLACK)
                binding.buttonToday.setBackgroundResource(R.drawable.back_button_week_unsel)
            }
            2 -> {
                binding.buttonYest.setTextColor(Color.BLACK)
                binding.buttonYest.setBackgroundResource(R.drawable.back_button_week_unsel)
            }
            3 -> {
                binding.buttonWeek.setTextColor(Color.BLACK)
                binding.buttonWeek.setBackgroundResource(R.drawable.back_button_week_unsel)
            }
            4 -> {
                binding.buttonMonth.setTextColor(Color.BLACK)
                binding.buttonMonth.setBackgroundResource(R.drawable.back_button_week_unsel)
            }
            5 -> {
                binding.button6Months.setTextColor(Color.BLACK)
                binding.button6Months.setBackgroundResource(R.drawable.back_button_week_unsel)
            }
            6 -> {
                binding.buttonYear.setTextColor(Color.BLACK)
                binding.buttonYear.setBackgroundResource(R.drawable.back_button_week_unsel)
            }
        }
    }


}