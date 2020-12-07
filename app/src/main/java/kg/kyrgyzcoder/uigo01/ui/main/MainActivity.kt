package kg.kyrgyzcoder.uigo01.ui.main

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import kg.kyrgyzcoder.uigo01.R
import kg.kyrgyzcoder.uigo01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sectionPagerAdapter: MainPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#f3f3f4")
        binding.navBottomView.setOnNavigationItemSelectedListener(this)
        sectionPagerAdapter = MainPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.viewPager.isUserInputEnabled = false

        viewPagerListener()

    }

    private fun viewPagerListener() {
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
                        binding.navBottomView.menu.findItem(R.id.report).isChecked = true
                    }
                    1 -> {
                        binding.navBottomView.menu.findItem(R.id.items).isChecked = true
                    }
                    2 -> {
                        binding.navBottomView.menu.findItem(R.id.sell).isChecked = true
                    }
                    else -> {
                        binding.navBottomView.menu.findItem(R.id.more).isChecked = true
                    }
                }
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.viewPager.currentItem = when (item.itemId) {
            R.id.report -> {
                0
            }
            R.id.items -> {
                1
            }
            R.id.sell -> {
                2
            }
            else -> {
                3
            }
        }

        return true
    }
}