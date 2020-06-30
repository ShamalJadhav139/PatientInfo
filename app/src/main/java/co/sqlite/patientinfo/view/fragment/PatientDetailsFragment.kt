package co.sqlite.patientinfo.view.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import co.sqlite.patientinfo.R
import co.sqlite.patientinfo.databinding.FragmentPatientDetailsBinding
import co.sqlite.patientinfo.handler.DatabaseHandler
import co.sqlite.patientinfo.model.PatientModelClass
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class PatientDetailsFragment: Fragment()  {
    private var binding:FragmentPatientDetailsBinding ? = null
    var callBack:((Int)->Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_patient_details, container, false)
        binding!!.submitBtn.setOnClickListener {
            saveRecord()
        }
        binding!!.dob.setOnClickListener {
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val m = month - 1
                binding!!.dob.text = convertYYYYMMddToddMMMYYYY("" + year + "-" + m + "-" + dayOfMonth)
            }, mYear, mMonth, mDay)
            datePickerDialog.datePicker.maxDate = c.timeInMillis
            datePickerDialog.show()
        }
        return binding!!.root
    }

    //method for saving records in database
    fun saveRecord(){
        val id = binding!!.uhid.text.toString()
        val paitentName = binding!!.paitentName.text.toString()
        val age = binding!!.age.text.toString()
        val address = binding!!.address.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(context!!)
        if(id.trim()!="" && paitentName.trim()!="" && age.trim()!="" && address.trim()!=""){
            val status = databaseHandler.addEmployee(PatientModelClass(Integer.parseInt(id),paitentName, age, address))
            if(status > -1){
                Toast.makeText(context,"record save", Toast.LENGTH_LONG).show()
                binding!!.uhid.text.clear()
                binding!!.paitentName.text.clear()
                binding!!.age.text.clear()
                binding!!.address.text.clear()
            }
        }else{
            Toast.makeText(context,"id or name or email cannot be blank", Toast.LENGTH_LONG).show()
        }

    }

    fun convertYYYYMMddToddMMMYYYY(yyyyMMdd: String): String {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val dateObj = sdf.parse(yyyyMMdd)
            println()
            return SimpleDateFormat("dd MMM yy").format(dateObj)
        } catch (e: ParseException) {
            e.printStackTrace()
            return yyyyMMdd
        }

    }



}