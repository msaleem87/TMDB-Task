package sa.net.ibtikar.android.models.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import sa.net.ibtikar.android.models.network.PersonDetail
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "People", primaryKeys = ["id"])
data class Person(
        var page: Int,
        @Embedded var personDetail: PersonDetail? = null,
        val profile_path: String?,
        val adult: Boolean,
        val id: Int,
        val name: String,
        val popularity: Float
) : Parcelable
