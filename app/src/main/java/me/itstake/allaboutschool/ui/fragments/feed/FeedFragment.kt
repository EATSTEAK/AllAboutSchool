package me.itstake.allaboutschool.ui.fragments.feed


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.feed_fragment.*
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.SharedViewModel
import me.itstake.allaboutschool.data.feed.FeedData
import me.itstake.allaboutschool.ui.adapters.FeedAdapter
import me.itstake.allaboutschool.ui.listeners.HideBottomNavOnScrollListener


class FeedFragment : Fragment() {

    private lateinit var model: SharedViewModel
    companion object {
        fun newInstance() = FeedFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
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
            adapter = FeedAdapter(arrayOf(FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!"), FeedData("Hello", "World!")))

        }
        feed_recycler.addOnScrollListener(HideBottomNavOnScrollListener(model))

        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
