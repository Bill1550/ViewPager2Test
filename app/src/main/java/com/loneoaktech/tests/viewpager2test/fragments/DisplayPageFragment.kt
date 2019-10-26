package com.loneoaktech.tests.viewpager2test.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.loneoaktech.utility.delegates.BundleIntDelegate

/**
 *  A page displayed in the ViewPager2
 */
class DisplayPageFragment : Fragment() {

    companion object {

        var Bundle.pageNumber: Int? by BundleIntDelegate()
    }


}