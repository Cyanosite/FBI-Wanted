package hu.bme.aut.android.fbiwanted.feature.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hu.bme.aut.android.fbiwanted.R
import hu.bme.aut.android.fbiwanted.databinding.ItemPersonBinding
import hu.bme.aut.android.fbiwanted.model.WantedPersonData

class ListAdapter(
    private var wantedPeople: List<WantedPersonData>,
    private val listener: OnPersonSelectedListener
) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    interface OnPersonSelectedListener {
        fun onPersonSelected(person: WantedPersonData)
    }

    fun addItem(wantedPersonData: WantedPersonData) {
        wantedPeople += wantedPersonData
        notifyItemInserted(wantedPeople.size - 1)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = wantedPeople[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = wantedPeople.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemPersonBinding.bind(itemView)
        private var item: WantedPersonData? = null

        init {
            binding.root.setOnClickListener { listener.onPersonSelected(item!!) }
        }

        fun bind(newPerson: WantedPersonData) {
            item = newPerson
            val sex = item!!.sex?.lowercase() ?: "male"
            Glide.with(itemView)
                .load(item!!.images?.get(0)?.thumb)
                .placeholder(if (sex == "male") R.drawable.placeholder else R.drawable.placeholder_female)
                .transition(DrawableTransitionOptions().crossFade())
                .into(binding.ivPicture)
            item!!.title?.let { binding.tvTitle.text = it }
        }
    }
}