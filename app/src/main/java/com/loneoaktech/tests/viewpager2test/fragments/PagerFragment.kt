package com.loneoaktech.tests.viewpager2test.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.loneoaktech.tests.viewpager2test.R
import com.loneoaktech.utility.delegates.BundleIntDelegate
import kotlinx.android.synthetic.main.fragment_pager.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import timber.log.Timber

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

        var numPages: Int = 0
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

    private lateinit var adapter: PagerAdapter  // Would prefer to use "by lazy", but need a fresh adapter on restarts

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pager, container, false).apply {

            // create a fresh adapter
            viewPager.adapter = PagerAdapter(this@PagerFragment).apply {
                adapter = this
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //--- selecting page immediately doesn't work on a new load if adapter has already been loaded, always goes to page 0
//         view.goToSelectedPage()

        //--- delaying a bit works, but it goes to page 0 first.
//        lifecycleScope.launch {
//            delay(100)  // simulate a data load
//            loadAdapter()
//            view.goToSelectedPage()
//        }

        lifecycleScope.launch {
            delay(10) // simulate data load
//            loadAdapter() // --- Loading adapter here will cause pager to go to page 0 unless delay above is long enough
                            // --- fragment to started stated.
            lifecycleScope.launchWhenStarted {
                loadAdapter()   // avoids temporary visit to page 0.
                view.goToSelectedPage()
            }
        }

    }

    private fun View.goToSelectedPage() {
        arguments?.currentPage?.let { selectedPage ->
            selectPage(selectedPage)
        }
    }

    private fun View.selectPage( selectedPage: Int ){
        Timber.i("Selecting page $selectedPage")
        viewPager?.apply {
            currentItem = selectedPage
        } ?: Timber.e("viewPager not available")
    }

    private fun loadAdapter() {

        // Set number of pages, to simulate loading the adapter.
        adapter.numPages = 10
    }


}