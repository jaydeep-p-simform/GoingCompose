package goingcompose.ui.sample

import android.view.View
import goingcompose.R
import goingcompose.ui.base.BaseRecyclerAdapter
import goingcompose.data.remote.response.User

class UserAdapter : BaseRecyclerAdapter<User>() {
    override fun getLayoutIdForType(viewType: Int): Int = if (viewType == ITEM_TYPE_NORMAL)
        R.layout.item_user
    else
        R.layout.layout_loader

    override fun onItemClick(view: View?, position: Int) { /* no-op */ }

    override fun areItemsSame(firstItem: User, secondItem: User): Boolean = firstItem == secondItem

    override fun isLastItemLoading(): Boolean = arrayList.lastOrNull()?.login?.uuid.isNullOrBlank()

    override fun isItemLoading(index: Int): Boolean = arrayList[index].login.uuid.isBlank()

    override fun getLoaderItem(): User = User()
}
