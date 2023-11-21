package hu.bme.aut.android.fbiwanted.feature.details

import hu.bme.aut.android.fbiwanted.model.WantedPersonData

interface WantedPersonDataHolder {
    fun getWantedPersonData(): WantedPersonData?
}