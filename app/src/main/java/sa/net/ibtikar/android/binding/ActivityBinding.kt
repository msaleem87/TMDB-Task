package sa.net.ibtikar.android.binding

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import sa.net.ibtikar.android.R
import sa.net.ibtikar.android.extension.checkIsMaterialVersion
import sa.net.ibtikar.android.extension.getStatusBarSize

fun AppCompatActivity.simpleToolbarWithHome(toolbar: MaterialToolbar, title_: String = "") {
  setSupportActionBar(toolbar)
  supportActionBar?.run {
    setDisplayHomeAsUpEnabled(true)
    setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
    title = title_
  }
  if (checkIsMaterialVersion() && toolbar.layoutParams is CollapsingToolbarLayout.LayoutParams) {
    toolbar.layoutParams = (toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams).apply {
      topMargin = getStatusBarSize()
    }
  }
}

@BindingAdapter("simpleToolbarWithHome", "simpleToolbarTitle")
fun bindToolbarWithTitle(toolbar: MaterialToolbar, activity: AppCompatActivity, title: String) {
  activity.simpleToolbarWithHome(toolbar, title)
}
