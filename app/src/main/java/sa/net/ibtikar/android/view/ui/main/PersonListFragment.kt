package sa.net.ibtikar.android.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sa.net.ibtikar.android.R
import sa.net.ibtikar.android.compose.ViewModelFragment
import sa.net.ibtikar.android.databinding.MainFragmentStarBinding
import sa.net.ibtikar.android.view.adapter.PeopleAdapter
import org.koin.android.viewmodel.ext.android.getViewModel

class PersonListFragment : ViewModelFragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return binding<MainFragmentStarBinding>(inflater, R.layout.main_fragment_star,
      container).apply {
      viewModel = getViewModel<MainActivityViewModel>().apply { postPeoplePage(1) }
      lifecycleOwner = this@PersonListFragment
      adapter = PeopleAdapter()
    }.root
  }
}
