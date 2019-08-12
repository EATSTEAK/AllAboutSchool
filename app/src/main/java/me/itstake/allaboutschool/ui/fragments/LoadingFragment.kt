package me.itstake.allaboutschool.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_loading.*
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.ui.fragments.meals.MealMenuFragment

class LoadingFragment : Fragment() {

    companion object {
        const val ARG_ERROR = "error"
        fun newInstance(errorCode: Int): LoadingFragment {
            val fragment = LoadingFragment()
            val args = Bundle()
            args.putInt(ARG_ERROR, errorCode)
            fragment.arguments = args
            return fragment
        }
    }

    private var errorCode: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        if(arguments != null) {
            errorCode = arguments?.getInt(ARG_ERROR) ?: 0
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(errorCode != 0) {
            progressBar.visibility = View.GONE
            error_message.text = when(errorCode) {
                1 -> this.getString(R.string.wrong_school_short)
                2 -> this.getString(R.string.no_internet)
                else -> this.getString(R.string.error)
            }
            error_message.visibility = View.VISIBLE
        }
    }
}
