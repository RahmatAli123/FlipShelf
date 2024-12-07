package com.example.flipshelf.Fragment

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ContextMenu
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flipshelf.Model.userModel
import com.example.flipshelf.R
import com.example.flipshelf.RecyclerAdapter


class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: RecyclerAdapter
    private lateinit var myList: ArrayList<userModel>
    private lateinit var drawerImageView: ImageView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var action: ActionBarDrawerToggle


    @SuppressLint("NotifyDataSetChanged", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = layout.findViewById(R.id.recyclerView)
        drawerImageView = layout.findViewById(R.id.drawer_ImageView)
        drawerLayout = layout.findViewById(R.id.drawer_Layout)
        action = ActionBarDrawerToggle(requireActivity(),drawerLayout,R.string.nav_open,R.string.nav_close)
        drawerLayout.addDrawerListener(action)
        action.syncState()

        drawerImageView.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        myList = ArrayList()
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2,GridLayoutManager.VERTICAL,false)
        myAdapter = RecyclerAdapter( myList)
        recyclerView.adapter = myAdapter

      myList.add(userModel("https://th.bing.com/th/id/OIP.TTHpYiI1eIc5cj3Mmhv1EwHaHa?pid=ImgDet&w=192&h=192&c=7&dpr=2","Nike Sportswear Club Fleece","\$99"))
        myList.add(userModel("https://hafriscos.pt/wp-content/uploads/2019/04/JAKE-MEN_02084_JAKE-WOMEN_02085_EXT.jpg","jfjkv","iuerhu"))
        myList.add(userModel("https://th.bing.com/th/id/OIP.r-mVeir7KJgCDCJeiQ8sQgHaLG?pid=ImgDet&w=192&h=288&c=7&dpr=2","jfjkv","iuerhu"))
        myList.add(userModel("https://th.bing.com/th/id/OIP.qs_1Wwoehu8iseE5fAhouQHaLG?pid=ImgDet&w=192&h=288&c=7&dpr=2","jfjkv","iuerhu"))
        myList.add(userModel("https://th.bing.com/th/id/OIP.bTzxK8nwSzEbzbHJde1rTgHaLH?pid=ImgDet&w=192&h=288&c=7&dpr=2","jfjkv","iuerhu"))
        myList.add(userModel("https://th.bing.com/th/id/OIP.HjuCRUEuEYdxF0eZVeyX8QHaJU?pid=ImgDet&w=192&h=241&c=7&dpr=2","jfjkv","iuerhu"))
        myList.add(userModel("https://th.bing.com/th/id/OIP.ohKcTwAA12AKXNLmPvtFPgHaJw?pid=ImgDet&w=192&h=252&c=7&dpr=2","jfjkv","iuerhu"))
        myList.add(userModel("https://th.bing.com/th/id/OIP.m0Fgt1WhlhB_Syn5CEqZ6gHaLD?pid=ImgDet&w=192&h=285&c=7&dpr=2","jfjkv","iuerhu"))
        myAdapter.notifyDataSetChanged()



        return layout

    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.drawer_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)

    }



}