package ie.elliot.uldashnav.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ie.elliot.uldashnav.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = MainPagerAdapter(5, listHeader)
    }
}
