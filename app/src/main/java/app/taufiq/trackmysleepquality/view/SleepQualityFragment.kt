package app.taufiq.trackmysleepquality.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import app.taufiq.trackmysleepquality.R
import app.taufiq.trackmysleepquality.databinding.FragmentSleepQualityBinding


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
        val binding = DataBindingUtil.inflate<FragmentSleepQualityBinding>(inflater,R.layout.fragment_sleep_quality,
        container,false)
        return binding.root
        // return inflater.inflate(R.layout.fragment_sleep_quality, container, false)
    }

}


