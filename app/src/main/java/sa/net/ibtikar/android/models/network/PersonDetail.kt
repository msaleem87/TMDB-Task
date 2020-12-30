package sa.net.ibtikar.android.models.network

import android.os.Parcelable
import sa.net.ibtikar.android.models.NetworkResponseModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonDetail(
  val birthday: String?,
  val known_for_department: String,
  val place_of_birth: String?,
  val biography: String
) : Parcelable, NetworkResponseModel
