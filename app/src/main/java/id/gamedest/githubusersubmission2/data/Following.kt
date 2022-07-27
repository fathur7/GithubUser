package id.gamedest.githubusersubmission2.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Following(
    var username: String? = "",
    var name: String? = "",
    var avatar: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: String? = "",
    var followers: String? = "",
    var following: String? = ""
) : Parcelable