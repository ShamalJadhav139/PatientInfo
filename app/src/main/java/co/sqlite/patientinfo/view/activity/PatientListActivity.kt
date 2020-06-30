package co.sqlite.patientinfo.view.activity

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import co.sqlite.patientinfo.R
import co.sqlite.patientinfo.adapter.PatientListAdapter
import co.sqlite.patientinfo.databinding.FragmentPatientListBinding
import co.sqlite.patientinfo.handler.DatabaseHandler
import co.sqlite.patientinfo.model.PatientModelClass
import co.sqlite.patientinfo.view.fragment.PatientDetailsFragment

class PatientListActivity : AppCompatActivity() {
    private var binding: FragmentPatientListBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_patient_list)
        init()
    }

    fun init(){
        viewRecord()
        binding!!.newPatientBtn.setOnClickListener {
            launchPatientDetailsFragment()
        }
        binding!!.refreshBtn.setOnClickListener {
            viewRecord()
        }
    }
    private fun launchFragment(fragment: Fragment, fragmentTitle: String) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_layout, fragment, fragmentTitle)
            .addToBackStack(getString(R.string.patient_info))
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
            .commitAllowingStateLoss()
    }

    private fun launchPatientDetailsFragment(){
        launchFragment(PatientDetailsFragment(), getString(R.string.patient_details))
    }

    private fun viewRecord(){

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        val emp: List<PatientModelClass> = databaseHandler.viewEmployee()
        val empArrayId = Array<String>(emp.size){"0"}
        val empArrayPatientName = Array<String>(emp.size){"null"}
        val empArrayAge = Array<String>(emp.size){"null"}
        val empArrayAddress = Array<String>(emp.size){"null"}
        for((index, e) in emp.withIndex()){
            empArrayId[index] = e.userId.toString()
            empArrayPatientName[index] = e.patientName
            empArrayAge[index] = e.patientAge
            empArrayAddress[index] = e.patientAddress
        }

        val myListAdapter = PatientListAdapter(this,empArrayId,empArrayPatientName,empArrayAge,empArrayAddress)
        binding!!.listView.adapter = myListAdapter
    }






}


