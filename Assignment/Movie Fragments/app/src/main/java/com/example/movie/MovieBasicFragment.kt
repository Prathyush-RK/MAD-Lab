package com.example.movie

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

class MovieBasicFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movie_basic, container, false)
    }
}