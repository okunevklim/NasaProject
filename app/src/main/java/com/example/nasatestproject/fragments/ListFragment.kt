package com.example.nasatestproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasatestproject.R
import com.example.nasatestproject.adapters.NasaAdapter
import com.example.nasatestproject.databinding.FragmentListBinding
import com.example.nasatestproject.interfaces.OnPostClickListener
import com.example.nasatestproject.models.NasaPost
import com.example.nasatestproject.presenters.ListPresenter
import com.example.nasatestproject.utils.Constants.REQUEST_CODE
import com.example.nasatestproject.utils.InternetConnectionChecker
import com.example.nasatestproject.utils.SnackBarHelper
import com.example.nasatestproject.viewmodels.RoomViewModel
import com.example.nasatestproject.views.ListView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class ListFragment : MvpAppCompatFragment(), ListView, OnPostClickListener {
    private lateinit var binding: FragmentListBinding
    private lateinit var roomViewModel: RoomViewModel
    private val nasaPosts = arrayListOf<NasaPost>()
    private val adapter = NasaAdapter(nasaPosts, this)
    private val presenter by moxyPresenter { ListPresenter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomViewModel = ViewModelProvider(requireActivity()).get(RoomViewModel::class.java)
        binding.nasaRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.nasaRecycler.adapter = adapter
        InternetConnectionChecker.checkInternetConnection(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                Log.i("NASA", "ListFragment is connected, checking database...")
                presenter.checkDatabase(roomViewModel, true, silentMode = false)
            } else {
                Log.i("NASA", "ListFragment is not connected, checking database...")
                presenter.checkDatabase(roomViewModel, false, silentMode = true)
                showSnackBar(
                    R.string.no_internet_title,
                    R.drawable.bg_rounded_error_snackbar,
                    R.drawable.ic_exclamation
                )
            }
        }
    }

    override fun setNasaPhotos(nasaPhotos: ArrayList<NasaPost>) {
        adapter.updateNasaPosts(nasaPhotos)
    }

    override fun showSnackBar(title: Int, backgroundColorID: Int, imageID: Int) {
        requireActivity().runOnUiThread {
            SnackBarHelper.showSnackBar(
                binding.fragmentList,
                getString(title),
                backgroundColorID,
                imageID
            )
        }
    }

    override fun handlePosts(nasaPosts: List<NasaPost>) {
        val nasaArray = arrayListOf<NasaPost>()
        nasaArray.clear()
        nasaArray.addAll(nasaPosts)
        Log.i("NASA", "handleeeeeeeeeeeed")
        adapter.updateNasaPosts(nasaArray)
    }

    override fun showAlertDialog(messageID: Int) {
        val alertDialog = AlertDialogFragment()
        val bundle = Bundle()
        bundle.putInt("alert_msg_res_id", messageID)
        alertDialog.arguments = bundle
        alertDialog.setTargetFragment(this, REQUEST_CODE)
        alertDialog.show(parentFragmentManager, "custom_dialog")
    }

    override fun onPostClick(nasaPost: NasaPost) {
        val bundle = Bundle()
        bundle.putSerializable("NASA", nasaPost)
        val navController = requireActivity().findNavController(R.id.navHostFragment)
        navController.navigate(R.id.action_see_details, bundle)
    }
}