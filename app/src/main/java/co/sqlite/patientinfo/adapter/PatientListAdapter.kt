package co.sqlite.patientinfo.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.sqlite.patientinfo.R
import co.sqlite.patientinfo.databinding.PatientListItemBinding

class PatientListAdapter(private var context: Activity,private val id: Array<String>, private val nameText: Array<String>, private val age: Array<String>, private val address: Array<String>)
    : ArrayAdapter<String>(context,R.layout.patient_list_item, nameText) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val rowView = LayoutInflater.from(parent.context).inflate(R.layout.patient_list_item, parent, false)
        val idText = rowView.findViewById(R.id.uhid) as TextView
        val patientNameText = rowView.findViewById(R.id.patientName) as TextView
        val ageText = rowView.findViewById(R.id.age) as TextView
        val addressText = rowView.findViewById(R.id.address) as TextView


        idText.text = "UHID: ${id[position]}"
        patientNameText.text = "Patient Name: ${nameText[position]}"
        ageText.text = "Age: ${age[position]}"
        addressText.text = "Address: ${address[position]}"
        return rowView
    }
}
