package hu.bme.aut.android.fbiwanted.model

data class WantedListData(
    var total: Int?,
    var page: Int?,
    var items: List<WantedPersonData>?
)