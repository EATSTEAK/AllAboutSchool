package me.itstake.allaboutschool.ui.listeners

import androidx.recyclerview.widget.RecyclerView
import me.itstake.allaboutschool.SharedViewModel

class HideBottomNavOnScrollListener(val model: SharedViewModel) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        model.bottomNavIsShow.value = dy > 0
        super.onScrolled(recyclerView, dx, dy)
    }

}