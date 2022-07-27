package id.gamedest.githubusersubmission2.viewmod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.gamedest.githubusersubmission2.setting.SettingPref
import java.lang.IllegalArgumentException

class ViewModelFactory(private val pref: SettingPref) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewMod::class.java)) {
            return SettingViewMod(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}