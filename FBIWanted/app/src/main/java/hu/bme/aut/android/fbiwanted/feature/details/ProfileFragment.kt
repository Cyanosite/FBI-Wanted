package hu.bme.aut.android.fbiwanted.feature.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hu.bme.aut.android.fbiwanted.R
import hu.bme.aut.android.fbiwanted.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var wantedPersonDataHolder: WantedPersonDataHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wantedPersonDataHolder = if (activity is WantedPersonDataHolder) {
            activity as WantedPersonDataHolder?
        } else {
            throw RuntimeException(
                "Activity must implement WantedPersonDataHolder interface!"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (wantedPersonDataHolder?.getWantedPersonData() != null) {
            displayPersonData()
        }
    }

    private fun displayPersonData() {
        val wantedPersonData = wantedPersonDataHolder?.getWantedPersonData() ?: return

        Glide.with(this)
            .load(wantedPersonData.images?.get(0)?.thumb)
            .placeholder(if (wantedPersonData.sex == "male") R.drawable.placeholder else R.drawable.placeholder_female)
            .transition(DrawableTransitionOptions().crossFade())
            .into(binding.ivPicture)

        wantedPersonData.title?.let { binding.tvTitle.text = it }
        wantedPersonData.eyes?.let { binding.tvEyes.text = it }
        wantedPersonData.hair?.let { binding.tvHair.text = it }
        wantedPersonData.build?.let { binding.tvBuild.text = it }
        wantedPersonData.sex?.let { binding.tvSex.text = it }
        wantedPersonData.race?.let { binding.tvRace.text = it }
        wantedPersonData.nationality?.let { binding.tvNationality.text = it }
        wantedPersonData.subjects?.let { binding.tvSubject.text = it[0] }

        val ageMin = wantedPersonData.age_min
        val ageMax = wantedPersonData.age_max
        if (ageMin == ageMax) {
            val age = "${ageMin ?: "unknown"}"
            binding.tvAge.text = age
        } else {
            val age = "${ageMin ?: "unknown"} - ${ageMax ?: "unknown"}"
            binding.tvAge.text = age
        }

        val weightMin = wantedPersonData.weight_min
        val weightMax = wantedPersonData.weight_max
        if (weightMin == weightMax) {
            val weight = "${weightMin ?: "unknown"}"
            binding.tvWeight.text = weight
        } else {
            val weight = "${weightMin ?: "unknown"} - ${weightMax ?: "unknown"}"
            binding.tvWeight.text = weight
        }


        val rewardMin = wantedPersonData.reward_min
        val rewardMax = wantedPersonData.reward_max
        if (rewardMin == rewardMax) {
            val reward = "${if (rewardMin == 0) "none" else rewardMin}"
            binding.tvReward.text = reward
        } else {
            val reward = "${rewardMin ?: "unknown"} - ${rewardMax ?: "unknown"}"
            binding.tvReward.text = reward
        }
    }
}