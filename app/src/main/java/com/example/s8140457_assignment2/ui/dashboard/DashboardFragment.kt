package com.example.s8140457_assignment2.ui.dashboard

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.s8140457_assignment2.data.remote.Entity
import com.example.s8140457_assignment2.databinding.FragmentDashboardBinding
import com.example.s8140457_assignment2.databinding.ItemEntityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _b: FragmentDashboardBinding? = null
    private val b get() = _b!!
    private val vm: DashboardViewModel by viewModels()
    private val args: DashboardFragmentArgs by navArgs()
    private lateinit var adapter: EntityAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentDashboardBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = EntityAdapter { e ->
            val action = DashboardFragmentDirections.actionDashboardToDetails(
                title = e.artworkTitle ?: "Unknown",
                artist = e.artist ?: "Unknown",
                medium = e.medium ?: "Unknown",
                year = e.year ?: 0,
                description = e.description ?: "No description"
            )
            findNavController().navigate(action)
        }
        b.rv.adapter = adapter

        vm.items.observe(viewLifecycleOwner) { adapter.submitList(it) }
        vm.error.observe(viewLifecycleOwner) { it?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() } }

        vm.load(args.keypass)
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}

private class EntityAdapter(
    val onClick: (Entity) -> Unit
) : ListAdapter<Entity, EntityVH>(
    object : DiffUtil.ItemCallback<Entity>() {
        override fun areItemsTheSame(old: Entity, new: Entity) =
            old.artworkTitle == new.artworkTitle && old.artist == new.artist
        override fun areContentsTheSame(old: Entity, new: Entity) = old == new
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityVH {
        val binding = ItemEntityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EntityVH(binding, onClick)
    }
    override fun onBindViewHolder(holder: EntityVH, position: Int) = holder.bind(getItem(position))
}

private class EntityVH(
    private val b: ItemEntityBinding,
    val onClick: (Entity) -> Unit
) : RecyclerView.ViewHolder(b.root) {
    fun bind(e: Entity) {
        b.tvTitle.text = e.artworkTitle ?: "Unknown"
        b.tvSubtitle.text = "${e.artist ?: "Unknown"} • ${e.year ?: "-"}"
        b.root.setOnClickListener { onClick(e) }
    }
}
