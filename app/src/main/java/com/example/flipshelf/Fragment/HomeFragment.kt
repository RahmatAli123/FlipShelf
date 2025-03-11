package com.example.flipshelf.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextSwitcher
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flipshelf.Activity.LoginActivity
import com.example.flipshelf.Model.profileModel
import com.example.flipshelf.Model.sellerModel
import com.example.flipshelf.Activity.ProductDetailsActivity
import com.example.flipshelf.Activity.ProfilesActivity
import com.example.flipshelf.R
import com.example.flipshelf.Adapter.RecyclerAdapter
import com.example.flipshelf.Activity.productDetailsInterface
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment(), productDetailsInterface {
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: RecyclerAdapter
    private lateinit var myList: ArrayList<sellerModel>
    private lateinit var drawerImageView: ImageView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var navigationView:NavigationView
    private var editProfileList = ArrayList<profileModel>()
    private lateinit var drawerProfileImage: ImageView
    private lateinit var drawerProfileName: TextView
    private lateinit var drawerProfileEmail: TextView
    private lateinit var searchView: EditText

    @SuppressLint("NotifyDataSetChanged", "MissingInflatedId", "InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = layout.findViewById(R.id.recyclerView)
        navigationView = layout.findViewById(R.id.navigation_view)
        drawerImageView = layout.findViewById(R.id.drawer_ImageView)
        drawerLayout = layout.findViewById(R.id.drawer_Layout)
        val navigationHeader=navigationView.getHeaderView(0)
        drawerProfileImage = navigationHeader.findViewById(R.id.userImage_ImageView)
        drawerProfileName = navigationHeader.findViewById(R.id.userName_TextView)
        drawerProfileEmail =navigationHeader.findViewById(R.id.userEmail_TextView)
        searchView = layout.findViewById(R.id.searchView)

        searchView.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
              filter(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        db = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Account_Information -> {
                    getProfileData()
                    true
                }

                R.id.nav_Password -> {
                    Toast.makeText(requireContext(), "Password", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_Oder -> {
                    Toast.makeText(requireContext(), "Order", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_Cards -> {
                    Toast.makeText(requireContext(), "My Cards", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_Wishlist -> {
                    Toast.makeText(requireContext(), "Wishlist", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_settings -> {
                    Toast.makeText(requireContext(), "Settings", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.logout -> {
                    val dialog = AlertDialog.Builder(requireContext())
                    dialog.setTitle("Logout")
                    dialog.setMessage("Are you sure you want to logout?")

                    dialog.setPositiveButton("Yes") { dialog, _ ->
                        FirebaseAuth.getInstance().signOut()
                        startActivity(Intent(requireContext(), LoginActivity::class.java))
                        requireActivity().finish()
                        Toast.makeText(
                            requireContext(),
                            "Logged out successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        dialog.dismiss()
                    }
                    dialog.setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }

                    dialog.create().show()
                    true
                }

                else -> {
                    false
                }
            }
        }

        drawerImageView.setOnClickListener {
            setProfileData()
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        myList = ArrayList()
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        myAdapter = RecyclerAdapter(myList, this)
        recyclerView.adapter = myAdapter
        getProductsData()
        return layout
    }

    private fun getProductsData() {
        db.getReference("products")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    myList.clear()
                    for (snap in snapshot.children) {
                        val data = snap.getValue(sellerModel::class.java)
                        myList.add(data!!)
                    }
                    myAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch products data: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getProfileData() {
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid!!
            db.getReference("users").child(uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        editProfileList.clear()
                        val data = snapshot.getValue(profileModel::class.java)
                            editProfileList.add(data!!)
                            val intent = Intent(requireContext(), ProfilesActivity::class.java)
                            intent.putExtra("name", data.name)
                            intent.putExtra("email", data.email)
                            intent.putExtra("image", data.image)
                            startActivity(intent)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            requireContext(),
                            "Failed to fetch profile data: ${error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

    }

    @SuppressLint("SuspiciousIndentation")
    private fun setProfileData() {
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid!!
            db.getReference("users").child(uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    @SuppressLint("SuspiciousIndentation")
                    override fun onDataChange(snapshot: DataSnapshot) {
                        editProfileList.clear()
                        val data = snapshot.getValue(profileModel::class.java)

                            editProfileList.add(data!!)
                            Glide.with(requireContext()).load(data.image).into(drawerProfileImage)
                            drawerProfileName.text = data.name
                            drawerProfileEmail.text = data.email

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            requireContext(),
                            "Failed to fetch profile data: ${error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

    }

    override fun productDetails(userDataModel: sellerModel) {
        val intent = Intent(requireContext(), ProductDetailsActivity::class.java)
        intent.putExtra("productImage", userDataModel.image)
        intent.putExtra("productName", userDataModel.name)
        intent.putExtra("productPrice", userDataModel.price)
        intent.putExtra("productDescription", userDataModel.description)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        setProfileData()
    }

    private fun filter(query: String) {
        val filteredList = ArrayList<sellerModel>()
        for (item in myList) {
            if (item.name?.contains(query, ignoreCase = true) == true ||
                item.description?.contains(query, ignoreCase = true) == true) {
                filteredList.add(item)
            }
        }
        myAdapter.updateList(filteredList)
    }


}
