package hu.bme.aut.android.fbiwanted.feature.details

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.fbiwanted.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
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
        binding = FragmentDetailsBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (wantedPersonDataHolder?.getWantedPersonData() != null) {
            displayCrimeData()
        }
    }

    private fun displayCrimeData() {
        val caution = wantedPersonDataHolder?.getWantedPersonData()?.caution
        val details = wantedPersonDataHolder?.getWantedPersonData()?.details
        val remarks = wantedPersonDataHolder?.getWantedPersonData()?.remarks
        if (details != null)
            binding.tvDetails.text =
                Html.fromHtml("<h1>Details</h1>\n$details", Html.FROM_HTML_MODE_LEGACY)
        if (caution != null)
            binding.tvCaution.text =
                Html.fromHtml("<h1>Caution</h1>\n$caution", Html.FROM_HTML_MODE_LEGACY)
        if (remarks != null) {
            binding.tvRemarks.text =
                Html.fromHtml("<h1>Remarks</h1>\n$remarks", Html.FROM_HTML_MODE_LEGACY)
        }
    }
}