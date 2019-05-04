package me.itstake.allaboutschool.ui.fragments.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.feed_fragment.*
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.data.feed.FeedData
import me.itstake.allaboutschool.ui.adapters.FeedAdapter


class FeedFragment : Fragment() {

    companion object {
        fun newInstance() = FeedFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.feed_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        feed_recycler.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = LinearLayoutManager(activity)

            // specify an viewAdapter (see also next example)
            adapter = FeedAdapter(arrayOf(FeedData("Hello", "World!")))

        }
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset)-appBarLayout.totalScrollRange >= -154 && Math.abs(verticalOffset)-appBarLayout.totalScrollRange <= 0) {
                // Collapsed
                collapsing_toolbar.title = getString(R.string.frag_feed)
            } else {
                // Expanded
                collapsing_toolbar.title = " "
            }
        })
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
