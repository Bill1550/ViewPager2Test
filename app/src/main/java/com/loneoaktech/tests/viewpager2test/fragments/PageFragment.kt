package com.loneoaktech.tests.viewpager2test.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.loneoaktech.utility.delegates.BundleIntDelegate
import timber.log.Timber

/**
 *  A page displayed in the ViewPager2
 */
class PageFragment : Fragment() {

    companion object {

        var Bundle.pageNumber: Int? by BundleIntDelegate()

        fun createInstance( pageNumber: Int): PageFragment {
            return PageFragment().apply { arguments = Bundle().apply {
                this.pageNumber = pageNumber }
            }
        }
    }

    val pageNumber: Int?
        get() = arguments?.pageNumber

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate, pageNumber=$pageNumber")
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart, pageNumber=$pageNumber")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume, pageNumber=$pageNumber")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause, pageNumber=$pageNumber")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop, pageNumber=$pageNumber")
    }
}