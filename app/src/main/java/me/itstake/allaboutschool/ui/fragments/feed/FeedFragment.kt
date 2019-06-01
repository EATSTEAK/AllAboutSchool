package me.itstake.allaboutschool.ui.fragments.feed


import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.feed_fragment.*
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.SharedViewModel
import me.itstake.allaboutschool.data.feed.FeedData
import me.itstake.allaboutschool.data.settings.SettingEnums
import me.itstake.allaboutschool.data.settings.SettingsManager
import me.itstake.allaboutschool.ui.adapters.FeedAdapter
import me.itstake.allaboutschool.ui.listeners.HideBottomNavOnScrollListener


class FeedFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    companion object {
        fun newInstance() = FeedFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        sharedViewModel = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val settingsManager = SettingsManager(activity?.applicationContext)
        //Recycler Init
        feed_recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = FeedAdapter(arrayOf(FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!")))
        }
        feed_recycler.addOnScrollListener(HideBottomNavOnScrollListener(sharedViewModel))

        // Custom Color
        feed_collapsing_toolbar.setBackgroundColor(Color.parseColor(settingsManager.getSettings(SettingEnums.GENERAL_PRIMARY_COLOR) as String))
        sharedViewModel.primaryColor.observe(this, Observer<String>{
            val color = Color.parseColor(it)
            val colorAni = ValueAnimator.ofObject(ArgbEvaluator(), (feed_collapsing_toolbar.background as ColorDrawable).color, color)
            colorAni.duration = 250
            colorAni.addUpdateListener {ani ->
                feed_collapsing_toolbar.setBackgroundColor(ani.animatedValue as Int)
                feed_collapsing_toolbar.setContentScrimColor(ani.animatedValue as Int)
            }
            colorAni.start()
        })

        //Bind ActionBar
        (activity as AppCompatActivity).setSupportActionBar(feed_toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        super.onActivityCreated(savedInstanceState)
    }

}
