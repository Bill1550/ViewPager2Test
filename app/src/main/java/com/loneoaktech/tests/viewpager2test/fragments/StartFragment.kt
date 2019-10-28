package com.loneoaktech.tests.viewpager2test.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.loneoaktech.tests.viewpager2test.R
import kotlinx.android.synthetic.main.fragment_start.view.*

/**
 * Starting fragment for the nav graph. loads the PagerFragment passing the page
 * number for the pager to start on.
 */
class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false).apply {

            goButton.setOnClickListener {
                pageNumberEditView.text?.toString()?.toIntOrNull()?.let { pageNumber ->

                    findNavController().navigate(
                        R.id.action_startFragment_to_pagerFragment,
                        PagerFragment.arguments(pageNumber)
                    )

                }

            }

        }
    }

}