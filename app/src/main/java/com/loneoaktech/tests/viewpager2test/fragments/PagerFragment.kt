package com.loneoaktech.tests.viewpager2test.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.loneoaktech.utility.delegates.BundleIntDelegate

/**
 * Fragment with the view pager.
 * Uses a start argument to determine which page to run.
 */
class PagerFragment : Fragment() {

    companion object {

        var Bundle.currentPage by BundleIntDelegate()

        fun arguments( pageNumber: Int ) = Bundle().apply {
            this.currentPage = pageNumber
        }
    }

    private class PagerAdapter( fragment: PagerFragment) : FragmentStateAdapter(fragment) {

        var numPages: Int = 10
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getItemCount(): Int {
            return numPages
        }

        override fun createFragment(position: Int): Fragment {
            require( position < numPages){ "invalid page number"}
            return PageFragment.createInstance(position)
        }
    }



}