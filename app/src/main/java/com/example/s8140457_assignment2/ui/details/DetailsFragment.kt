package com.example.s8140457_assignment2.ui.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.s8140457_assignment2.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var _b: FragmentDetailsBinding? = null
    private val b get() = _b!!
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentDetailsBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        b.tvTitle.text = args.title
        b.tvArtist.text = args.artist
        b.tvMediumYear.text = "${args.medium} • ${args.year}"
        b.tvDesc.text = args.description
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
