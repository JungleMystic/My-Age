package com.lrm.myage

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.lrm.myage.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.shimmer1.visibility = View.INVISIBLE
        binding.shimmer2.visibility = View.INVISIBLE
        binding.shimmer3.visibility = View.INVISIBLE
        binding.shimmer4.visibility = View.INVISIBLE
        binding.shimmer5.visibility = View.INVISIBLE
        binding.shimmer6.visibility = View.INVISIBLE
        binding.shimmer7.visibility = View.INVISIBLE
        binding.shimmerFullAge.visibility = View.INVISIBLE

        val bottomToTop1 = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top1)
        val bottomToTop2 = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top2)
        val bottomToTop3 = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top3)

        val scaleToBig = AnimationUtils.loadAnimation(this, R.anim.scale_to_big)

        binding.apply {

            getAgeButton.startAnimation(scaleToBig)
            datePicker.startAnimation(scaleToBig)

            yearsResultHead.startAnimation(bottomToTop1)
            monthsResultHead.startAnimation(bottomToTop1)
            weeksResultHead.startAnimation(bottomToTop1)

            daysResultHead.startAnimation(bottomToTop2)
            hoursResultHead.startAnimation(bottomToTop2)
            minutesResultHead.startAnimation(bottomToTop2)

            secondsResultHead.startAnimation(bottomToTop3)
        }

        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        val currentDate = "$currentDay/${currentMonth+1}/$currentYear"
        binding.currentDate.text = currentDate

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                _, dob_year, dob_month, dob_date ->

            val mCurrentDate = currentDay
            var mCurrentMonth = currentMonth
            var mCurrentYear = currentYear
            
            binding.dob.text = "$dob_date/${dob_month+1}/$dob_year"

            var differenceInYears: Int = 0
            var differenceInMonth:Int = 0
            var differenceInDate: Int = 0

            if (dob_date > mCurrentDate) {
                differenceInDate = (mCurrentDate+30) - dob_date
                mCurrentMonth -= 1
            } else {
                differenceInDate = mCurrentDate - dob_date
            }

            if (dob_month > mCurrentMonth) {
                differenceInMonth = (mCurrentMonth+12) - dob_month
                mCurrentYear -= 1
            } else {
                differenceInMonth = (mCurrentMonth - dob_month)
            }

            differenceInYears = (mCurrentYear - dob_year)

            val fullAge = "$differenceInYears years, $differenceInMonth months, $differenceInDate days"

            val selectedDate = binding.dob.text.toString()
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val cD = sdf.parse(currentDate)
            val sD = sdf.parse(selectedDate)

            val differenceOnlyInYears = differenceInYears
            val differenceOnlyInMonths = (differenceOnlyInYears * 12) + (differenceInMonth)
            val differenceOnlyInWeeks = ((cD.time) - (sD.time)) / (1000*60*60*24*7)
            val differenceOnlyInDays = ((cD.time) - (sD.time)) / (1000*60*60*24)
            val differenceInHours = ((cD.time) - (sD.time)) / (1000*60*60)
            val differenceInMinutes = ((cD.time) - (sD.time)) / (1000*60)
            val differenceInSeconds = ((cD.time) - (sD.time)) / (1000)


            binding.getAgeButton.setOnClickListener {
                binding.progressBar.visibility = View.VISIBLE

                binding.fullAgeResult.text = ""

                binding.shimmer1.visibility = View.VISIBLE
                binding.shimmer2.visibility = View.VISIBLE
                binding.shimmer3.visibility = View.VISIBLE
                binding.shimmer4.visibility = View.VISIBLE
                binding.shimmer5.visibility = View.VISIBLE
                binding.shimmer6.visibility = View.VISIBLE
                binding.shimmer7.visibility = View.VISIBLE
                binding.shimmerFullAge.visibility = View.VISIBLE

                binding.fullAgeResult.visibility = View.GONE
                binding.yearsResult.visibility = View.GONE
                binding.monthsResult.visibility = View.GONE
                binding.weeksResult.visibility = View.GONE
                binding.daysResult.visibility = View.GONE
                binding.hoursResult.visibility = View.GONE
                binding.minutesResult.visibility = View.GONE
                binding.secondsResult.visibility = View.GONE

                binding.shimmer1.startShimmer()
                binding.shimmer2.startShimmer()
                binding.shimmer3.startShimmer()
                binding.shimmer4.startShimmer()
                binding.shimmer5.startShimmer()
                binding.shimmer6.startShimmer()
                binding.shimmer7.startShimmer()
                binding.shimmerFullAge.startShimmer()

                Handler(Looper.myLooper()!!).postDelayed({
                    binding.fullAgeResult.text = resources.getString(R.string.my_age_is, fullAge)
                    binding.yearsResult.text = resources.getString(R.string.years_result, differenceOnlyInYears.toString())
                    binding.monthsResult.text = resources.getString(R.string.months_result, differenceOnlyInMonths.toString())
                    binding.weeksResult.text = resources.getString(R.string.weeks_result, differenceOnlyInWeeks.toString())
                    binding.daysResult.text = resources.getString(R.string.days_result, differenceOnlyInDays.toString())
                    binding.hoursResult.text = resources.getString(R.string.hours_result, differenceInHours.toString())
                    binding.minutesResult.text = resources.getString(R.string.minutes_result, differenceInMinutes.toString())
                    binding.secondsResult.text = resources.getString(R.string.seconds_result, differenceInSeconds.toString())

                    binding.progressBar.visibility = View.INVISIBLE

                    binding.shimmer1.stopShimmer()
                    binding.shimmer2.stopShimmer()
                    binding.shimmer3.stopShimmer()
                    binding.shimmer4.stopShimmer()
                    binding.shimmer5.stopShimmer()
                    binding.shimmer6.stopShimmer()
                    binding.shimmer7.stopShimmer()
                    binding.shimmerFullAge.stopShimmer()

                    binding.shimmer1.visibility = View.GONE
                    binding.shimmer2.visibility = View.GONE
                    binding.shimmer3.visibility = View.GONE
                    binding.shimmer4.visibility = View.GONE
                    binding.shimmer5.visibility = View.GONE
                    binding.shimmer6.visibility = View.GONE
                    binding.shimmer7.visibility = View.GONE
                    binding.shimmerFullAge.visibility = View.GONE

                    binding.fullAgeResult.visibility = View.VISIBLE
                    binding.yearsResult.visibility = View.VISIBLE
                    binding.monthsResult.visibility = View.VISIBLE
                    binding.weeksResult.visibility = View.VISIBLE
                    binding.daysResult.visibility = View.VISIBLE
                    binding.hoursResult.visibility = View.VISIBLE
                    binding.minutesResult.visibility = View.VISIBLE
                    binding.secondsResult.visibility = View.VISIBLE

                }, 3000)
            }
        }
        , currentYear, currentMonth, currentDay)

        binding.datePicker.setOnClickListener {
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        }
    }

    private var backPressedTwice = false

    override fun onBackPressed() {
        if (backPressedTwice) {
            super.onBackPressed()
        } else {
            backPressedTwice = true
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()

            Handler(Looper.myLooper()!!).postDelayed({backPressedTwice = false}, 2000)
        }
    }
}