package app.taufiq.trackmysleepquality.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import app.taufiq.trackmysleepquality.R
import app.taufiq.trackmysleepquality.databinding.FragmentSleepQualityBinding
import app.taufiq.trackmysleepquality.db.SleepDatabase
import app.taufiq.trackmysleepquality.viewmodel.SleepQualityViewModel
import app.taufiq.trackmysleepquality.viewmodel.SleepQualityViewModelFactory


/**
 * Fragment that displays a list of clickable icons,
 * each representing a sleep quality rating.
 * Once the user taps an icon, the quality is set in the current sleepNight
 * and the database is updated.
 */
class SleepQualityFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSleepQualityBinding>(
            inflater, R.layout.fragment_sleep_quality,
            container, false)

        val application = requireNotNull(this.activity).application


        // extract argument from bundle
        val arguments = SleepQualityFragmentArgs.fromBundle(requireArguments())
        // get data Source
        val dataSource = SleepDatabase.getInstance(application).SleepDatabaseDao
        // create factory
        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)
        // getViewModel reference
        val sleepQualityVm = ViewModelProviders.of(this, viewModelFactory)
            .get(SleepQualityViewModel::class.java)
        // add the view to the binding object
        binding.sleepQualityViewModel = sleepQualityVm
        // add observers
        sleepQualityVm._navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) { // observer state
                this.findNavController().navigate(SleepQualityFragmentDirections.toSleepTracker())
                sleepQualityVm.doneNavigating()
            }
        })

        return binding.root
    }

}


