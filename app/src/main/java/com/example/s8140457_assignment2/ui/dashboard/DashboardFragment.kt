package com.example.s8140457_assignment2.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.s8140457_assignment2.R
import com.example.s8140457_assignment2.data.remote.Entity
import com.example.s8140457_assignment2.databinding.FragmentDashboardBinding
import com.example.s8140457_assignment2.databinding.ItemEntityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _b: FragmentDashboardBinding? = null
    private val b get() = _b!!

    private val args by navArgs<DashboardFragmentArgs>()
    private val vm: DashboardViewModel by viewModels()

    private val adapter = EntityAdapter { entity ->
        // Coerce nullables -> non-null for Safe Args
        val action = DashboardFragmentDirections.actionDashboardToDetails(
            title       = entity.artworkTitle ?: "",
            artist      = entity.artist ?: "",
            medium      = entity.medium ?: "",
            year        = entity.year ?: 0,
            description = entity.description ?: ""
        )
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _b = FragmentDashboardBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView setup
        b.rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = this@DashboardFragment.adapter
        }

        // Observe data
        vm.items.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
        vm.error.observe(viewLifecycleOwner) { msg ->
            msg?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        }

        // Kick off load with keypass from Login
        vm.load(args.keypass)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}

/** RecyclerView adapter **/
private class EntityAdapter(
    private val onClick: (Entity) -> Unit
) : ListAdapter<Entity, EntityVH>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityVH {
        val binding = ItemEntityBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EntityVH(binding, onClick)
    }

    override fun onBindViewHolder(holder: EntityVH, position: Int) {
        holder.bind(getItem(position))
    }
}

private class EntityVH(
    private val b: ItemEntityBinding,
    private val onClick: (Entity) -> Unit
) : RecyclerView.ViewHolder(b.root) {

    fun bind(item: Entity) = with(b) {
        // Safe defaults for nullable API fields
        val title  = item.artworkTitle ?: "Untitled"
        val artist = item.artist ?: "Unknown"
        val medium = item.medium ?: "-"
        val year   = (item.year ?: 0).toString()

        tvTitle.text = title
        tvSubtitle.text = root.context.getString(
            R.string.art_subtitle, artist, medium, year
        )

        root.setOnClickListener { onClick(item) }
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<Entity>() {
    override fun areItemsTheSame(oldItem: Entity, newItem: Entity): Boolean =
        (oldItem.artworkTitle ?: "") == (newItem.artworkTitle ?: "") &&
                (oldItem.artist ?: "") == (newItem.artist ?: "")

    override fun areContentsTheSame(oldItem: Entity, newItem: Entity): Boolean =
        oldItem == newItem
}
