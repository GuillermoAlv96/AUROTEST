package Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A data model class for User with required fields.
 */
@Parcelize

data class ClientUser(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val image: String = "",
    val mobile: String = "",
    val gender: String = "",
    val profileCompleted: Int = 0
) : Parcelable