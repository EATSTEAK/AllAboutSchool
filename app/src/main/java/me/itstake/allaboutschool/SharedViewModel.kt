package me.itstake.allaboutschool

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    var bottomNavIsShow = MutableLiveData<Boolean>()
}