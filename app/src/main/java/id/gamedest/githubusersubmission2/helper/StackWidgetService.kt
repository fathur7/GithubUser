package id.gamedest.githubusersubmission2.helper

import android.content.Intent
import android.widget.RemoteViewsService
import id.gamedest.githubusersubmission2.adapter.StackRemoteViewsFactory

class StackWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}
