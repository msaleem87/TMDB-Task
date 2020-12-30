package sa.net.ibtikar.android.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import sa.net.ibtikar.android.models.entity.Person
import sa.net.ibtikar.android.view.adapter.PeopleAdapter
import sa.net.ibtikar.android.view.ui.main.MainActivityViewModel
import com.skydoves.whatif.whatIfNotNull

@BindingAdapter("adapter")
fun bindAdapter(view: RecyclerView, baseAdapter: BaseAdapter) {
  view.adapter = baseAdapter
}


@BindingAdapter("adapterPersonList")
fun bindAdapterPersonList(view: RecyclerView, people: List<Person>?) {
  people.whatIfNotNull {
    (view.adapter as? PeopleAdapter)?.addPeople(it)
  }
}

@BindingAdapter("paginationPersonList")
fun paginationPersonList(view: RecyclerView, viewModel: MainActivityViewModel) {
  RecyclerViewPaginator(
    recyclerView = view,
    isLoading = { false },
    loadMore = { viewModel.postPeoplePage(it) },
    onLast = { false }
  ).run {
    threshold = 4
    currentPage = 1
  }
}
