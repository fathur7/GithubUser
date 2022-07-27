package id.gamedest.githubusersubmission2.helper

import android.database.Cursor
import android.provider.BaseColumns._ID
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_AVATAR
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_COMPANY
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_LOCATION
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_NAME
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_USERNAME
import id.gamedest.githubusersubmission2.data.UserFavorite

object MappingHelper {

    fun mapCursorToArrayList(userFavoritesCursor: Cursor?): ArrayList<UserFavorite> {
        val userFavoriteList = ArrayList<UserFavorite>()
        userFavoritesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val avatar = getString(getColumnIndexOrThrow(COLUMN_AVATAR))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val username = getString(getColumnIndexOrThrow(COLUMN_USERNAME))
                val company = getString(getColumnIndexOrThrow(COLUMN_COMPANY))
                val location = getString(getColumnIndexOrThrow(COLUMN_LOCATION))
                userFavoriteList.add(UserFavorite(id, avatar, name, username, company, location))
            }
        }
        return userFavoriteList
    }
}