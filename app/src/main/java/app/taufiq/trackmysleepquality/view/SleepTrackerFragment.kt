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
import app.taufiq.trackmysleepquality.R
import app.taufiq.trackmysleepquality.databinding.FragmentSleepTrackerBinding
import app.taufiq.trackmysleepquality.db.SleepDatabase
import app.taufiq.trackmysleepquality.viewmodel.SleepTrackerViewModel
import app.taufiq.trackmysleepquality.viewmodel.SleepTrackerViewModelFactory


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


        return binding.root
    }

}