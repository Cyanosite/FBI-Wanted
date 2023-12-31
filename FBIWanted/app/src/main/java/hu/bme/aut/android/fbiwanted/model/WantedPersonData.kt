package hu.bme.aut.android.fbiwanted.model

data class WantedPersonData(
    val id: String?,
    val uid: String?,
    val subjects: List<String>?,
    val title: String?,
    val description: String?,
    val images: List<WantedImageData>?,
    val files: List<WantedFileData>?,
    val warning_message: String?,
    val remarks: String?,
    val details: String?,
    val additional_information: String?,
    val caution: String?,
    val reward_text: String?,
    val reward_min: Int?,
    val reward_max: Int?,
    val dates_of_birth_used: List<String>?,
    val place_of_birth: String?,
    val locations: List<String>?,
    val field_offices: List<String>?,
    val legat_names: List<String>?,
    val status: String?,
    val person_classification: String?,
    val poster_classification: String?,
    val ncic: String?,
    val age_min: Int?,
    val age_max: Int?,
    val weight_min: Int?,
    val weight_max: Int?,
    val height_min: Int?,
    val height_max: Int?,
    val eyes: String?,
    val hair: String?,
    val build: String?,
    val sex: String?,
    val race: String?,
    val nationality: String?,
    val scars_and_marks: String?,
    val complexion: String?,
    val occupations: List<String>?, // documentation error on FBI site, this is a List
    val possible_countries: List<String>?,
    val possible_states: List<String>?,
    val modified: String?,
    val publication: String?,
    val path: String?
)