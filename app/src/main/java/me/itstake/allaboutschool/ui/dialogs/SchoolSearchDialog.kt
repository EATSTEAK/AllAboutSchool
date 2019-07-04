package me.itstake.allaboutschool.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.school_search_dialog.*
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.ui.adapters.SchoolSearchRecyclerAdapter
import me.itstake.neisinfo.School

class SchoolSearchDialog(context: Context) : Dialog(context) {
    var onSelectionListener = object: OnSelectionListener {
        override fun onSelection(school: School) {
            // DO NOTHING
        }
    }
    private var schoolList = ArrayList<School>()
    private val searchAdapter = SchoolSearchRecyclerAdapter(schoolList, onSelectionListener)
    private val handler: Handler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            if(msg?.what == 0) {
                no_results.visibility = View.VISIBLE
                school_search_recycler.swapAdapter(SchoolSearchRecyclerAdapter(schoolList, onSelectionListener), false)
            }
            if(msg?.what == 1) {
                no_results.visibility = View.GONE
                school_search_recycler.swapAdapter(SchoolSearchRecyclerAdapter(schoolList, onSelectionListener), false)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.school_search_dialog)
        region_spinner.adapter = ArrayAdapter.createFromResource(context, R.array.regions, android.R.layout.simple_spinner_item)
        region_spinner.setSelection(0)
        school_search_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }
        school_search.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                Thread(Runnable {
                    try {
                        schoolList = School.findSchool(School.SchoolRegion.values()[region_spinner.selectedItemPosition], school_search.text.toString())
                    } catch(e: Exception) {
                        no_results.text = context.resources.getString(R.string.no_internet)
                    }
                    if(schoolList.size == 0) {
                        handler.sendEmptyMessage(0)
                    } else {
                        handler.sendEmptyMessage(1)
                    }
                }).start()
            }
            true
        }
    }
    interface OnSelectionListener {
        fun onSelection(school: School)
    }
}