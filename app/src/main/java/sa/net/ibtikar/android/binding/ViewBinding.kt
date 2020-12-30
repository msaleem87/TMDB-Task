package sa.net.ibtikar.android.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import sa.net.ibtikar.android.api.Api
import sa.net.ibtikar.android.extension.visible
import sa.net.ibtikar.android.models.entity.Person
import sa.net.ibtikar.android.models.network.PersonDetail
import com.skydoves.whatif.whatIfNotNull

@BindingAdapter("toast")
fun bindToast(view: View, text: LiveData<String>) {
  text.value.whatIfNotNull {
    Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show()
  }
}

@BindingAdapter("visibilityByResource")
fun bindVisibilityByResource(view: View, anyList: List<Any>?) {
  anyList.whatIfNotNull {
    view.visible()
  }
}

@BindingAdapter("biography")
fun bindBiography(view: TextView, personDetail: PersonDetail?) {
  view.text = personDetail?.biography
}


@BindingAdapter("bindBackDrop")
fun bindBackDrop(view: ImageView, person: Person) {
  person.profile_path.whatIfNotNull {
    Glide.with(view.context).load(Api.getBackdropPath(it))
      .apply(RequestOptions().circleCrop())
      .into(view)
  }
}
