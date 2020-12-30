package sa.net.ibtikar.android.view.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import sa.net.ibtikar.android.api.Api
import sa.net.ibtikar.android.models.entity.Person
import sa.net.ibtikar.android.view.ui.details.person.PersonDetailActivity
import kotlinx.android.synthetic.main.item_person.view.*

class PeopleViewHolder(val view: View) : BaseViewHolder(view) {

  private lateinit var person: Person
  private lateinit var profileURL: String

  @Throws(Exception::class)
  override fun bindData(data: Any) {
    if (data is Person) {
      person = data
      drawItem()
    }
  }

  private fun drawItem() {
    itemView.run {
      item_person_name.text = person.name

      person.profile_path?.let {
        Glide.with(context)
          .load(Api.getPosterPath(it))
          .apply(RequestOptions().circleCrop())
          .into(item_person_profile)

        profileURL=Api.getPosterPath(it)
      }
    }
  }

  override fun onClick(p0: View?) = PersonDetailActivity.startActivity(context(), person, profileURL, itemView.item_person_profile)

  override fun onLongClick(p0: View?) = false
}
