package sa.net.ibtikar.android.models.network

import sa.net.ibtikar.android.models.NetworkResponseModel
import sa.net.ibtikar.android.models.entity.Person

data class PeopleResponse(
        val page: Int,
        val results: List<Person>,
        val total_results: Int,
        val total_pages: Int
) : NetworkResponseModel
