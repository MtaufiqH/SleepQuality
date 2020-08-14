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
import app.taufiq.trackmysleepquality.databinding.SleepDetailFragmentBinding
import app.taufiq.trackmysleepquality.db.SleepDatabase
import app.taufiq.trackmysleepquality.viewmodel.SleepDetailViewModel
import app.taufiq.trackmysleepquality.viewmodel.SleepDetailViewModelFactory


class SleepDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // get reference to the binding object and inflate the fragment views
        val binding: SleepDetailFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.sleep_detail_fragment,container, false)

        val application = requireNotNull(this.activity).application
        val arguments = SleepDetailFragmentArgs.fromBundle(requireArguments())

        // create an instance of the viewModel Factory
        val dataSource = SleepDatabase.getInstance(application).SleepDatabaseDao
        val viewModelFactory = SleepDetailViewModelFactory(arguments.sleepNightKey,dataSource)

        // get the viewModel reference and associated with this fragment
        val sleepDetailViewModel = ViewModelProviders.of(this,viewModelFactory).get(SleepDetailViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.sleepDetailViewModel = sleepDetailViewModel

        binding.lifecycleOwner = this

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        sleepDetailViewModel._navigateToSleepTracker.observe(viewLifecycleOwner, Observer { isTrue ->
            if (isTrue == true){
                this.findNavController().navigate(SleepDetailFragmentDirections.detailSleepBackToTracker())
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                sleepDetailViewModel.doneNavigating()
            }
        })


        return binding.root

    }

}