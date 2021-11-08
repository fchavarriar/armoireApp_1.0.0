package com.androidkotlin.armoireapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class RecipesFragment : Fragment() {
    private lateinit var demoCollectionPagerAdapter: DemoCollectionPagerAdapter
    private lateinit var viewPager: ViewPager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_recipes, container, false)
        val create = v.findViewById<FloatingActionButton>(R.id.create_recipes)
        create.setOnClickListener{
            val intent = Intent(activity, CreateRecipesActivity::class.java)
            activity!!.startActivity(intent)
        }
        return v
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        demoCollectionPagerAdapter = DemoCollectionPagerAdapter(childFragmentManager)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = demoCollectionPagerAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_container)
        tabLayout.setupWithViewPager(viewPager)
    }
}
class DemoCollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(i: Int): Fragment {
        val fragment: Fragment
        when (i) {
            0 -> fragment = BreakfastsFragment()
            1 -> fragment = LunchesFragment()
            2 -> fragment = DinnerFragment()
            else -> fragment = LivingFragment ()
        }
        return fragment
    }
    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Desayunos"
            1 -> return "Almuerzos"
            2 -> return "Cenas"
        }
        return null
    }
}
