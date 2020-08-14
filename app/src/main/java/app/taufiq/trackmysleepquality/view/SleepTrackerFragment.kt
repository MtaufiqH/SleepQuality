package app.taufiq.trackmysleepquality.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import app.taufiq.trackmysleepquality.R
import app.taufiq.trackmysleepquality.adapter.SleepNightAdapter
import app.taufiq.trackmysleepquality.databinding.FragmentSleepTrackerBinding
import app.taufiq.trackmysleepquality.db.SleepDatabase
import app.taufiq.trackmysleepquality.viewmodel.SleepTrackerViewModel
import app.taufiq.trackmysleepquality.viewmodel.SleepTrackerViewModelFactory
import com.google.android.material.snackbar.Snackbar


/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 * (Because we have not learned about RecyclerView yet.)
 */
class SleepTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSleepTrackerBinding>(inflater,
            R.layout.fragment_sleep_tracker, container, false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = SleepDatabase.getInstance(application).SleepDatabaseDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource,application)

        // Get a reference to the ViewModel associated with this fragment.
        val sleepTrackerViewModels = ViewModelProviders.of(this,viewModelFactory).get(SleepTrackerViewModel::class.java)

        // set this fragment as lifeCycle owner
        binding.lifecycleOwner = this


        // assign data variable in to the @sleepTrackerViewModel
        binding.sleepTrackerViewModel = sleepTrackerViewModels


        sleepTrackerViewModels._navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                this.findNavController().navigate(SleepTrackerFragmentDirections.toSleepQuality(night.nightId))
                sleepTrackerViewModels.doneNavigating()
            }

        })

        sleepTrackerViewModels._showSnackbars.observe(viewLifecycleOwner, Observer {
            if (it == true){
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                sleepTrackerViewModels.doneShowingSnackbar()
            }
        })


        val adapter = SleepNightAdapter()
        binding.sleepList.adapter = adapter

        sleepTrackerViewModels.nights.observe(viewLifecycleOwner, Observer { allNights ->
            allNights?.let {
                /*the ListAdapter diffs the new list against the old one and detects items
                 that were added, removed, moved, or changed.
                 Then the ListAdapter updates the items shown by RecyclerView.*/
                adapter.submitList(allNights)
            }
        })


        //setup grid Layout
        val gridLayout = GridLayoutManager(activity,3)
        // optional
        val manager = GridLayoutManager(activity, 5, GridLayoutManager.HORIZONTAL, false)
        binding.sleepList.layoutManager = gridLayout


        return binding.root
    }

}