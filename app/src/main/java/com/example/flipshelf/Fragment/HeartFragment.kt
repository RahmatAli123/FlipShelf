package com.example.flipshelf.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flipshelf.Adapter.WalletAdapter
import com.example.flipshelf.Model.productModel
import com.example.flipshelf.R
import com.example.flipshelf.Activity.productObject
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HeartFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WalletAdapter
    private lateinit var walletList: ArrayList<productModel>
    private lateinit var searchViewEditText:EditText
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_heart, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        searchViewEditText=view.findViewById(R.id.productSearchView)
        backButton=view.findViewById(R.id.backBtn_ImageView)
        walletList = ArrayList()
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        adapter = WalletAdapter(walletList)
        recyclerView.adapter = adapter

        backButton.setOnClickListener {

        }
        searchViewEditText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.filter(p0.toString())

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


        //Apl Call
        productObject.retrofit.getProduct().enqueue(object : Callback<List<productModel>> {

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<productModel>>, response: Response<List<productModel>>
            ) {
                if (response.isSuccessful) {
                    val data=response.body()
                    walletList.clear()
                    walletList.addAll(data!!)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<productModel>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error:${t.message}", Toast.LENGTH_SHORT).show()
            }

        })



        return view
    }






}