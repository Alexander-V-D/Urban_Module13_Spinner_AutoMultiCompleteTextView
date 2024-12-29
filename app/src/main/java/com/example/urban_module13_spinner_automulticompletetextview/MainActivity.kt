package com.example.urban_module13_spinner_automulticompletetextview

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var mainToolbar: Toolbar
    private lateinit var nameET: EditText
    private lateinit var surnameET: EditText
    private lateinit var ageET: EditText
    private lateinit var roleSpinner: Spinner
    private lateinit var saveBTN: Button
    private lateinit var personsLV: ListView

    private val persons = mutableListOf<Person>()
    private val roles = arrayOf(
        "Должность",
        "Инженер",
        "Конструктор",
        "Проектировщик",
        "Мастер",
        "Энергетик",
        "Механик"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainToolbar = findViewById(R.id.mainToolbar)
        nameET = findViewById(R.id.nameET)
        surnameET = findViewById(R.id.surnameET)
        ageET = findViewById(R.id.ageET)
        roleSpinner = findViewById(R.id.roleSpinner)
        saveBTN = findViewById(R.id.saveBTN)
        personsLV = findViewById(R.id.personsLV)

        setSupportActionBar(mainToolbar)
        title = getString(R.string.toolbar_title)

        val personsAdapter = ListAdapter(this, persons)

        val roleSpinnerAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, roles
        )
        roleSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = roleSpinnerAdapter

        saveBTN.setOnClickListener {
            addPerson()
            personsLV.adapter = personsAdapter
            personsAdapter.notifyDataSetChanged()
            clearPersonConstructor()
        }

        personsLV.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, l ->
                persons.remove(personsAdapter.getItem(position))
                personsAdapter.notifyDataSetChanged()
            }
    }

    private fun addPerson() {
        val name = nameET.text.toString()
        val surname = surnameET.text.toString()
        val age = ageET.text.toString()
        val role = roleSpinner.selectedItem.toString()
        persons.add(Person(name, surname, age, role))
    }

    private fun clearPersonConstructor() {
        nameET.text.clear()
        surnameET.text.clear()
        ageET.text.clear()
        roleSpinner.setSelection(0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val spinnerItem = menu?.findItem(R.id.toolbarRoleSpinner)
        val toolbarRoleSpinner = spinnerItem?.actionView as Spinner
        val toolbarRoleSpinnerAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, roles
        )
        toolbarRoleSpinner.adapter = toolbarRoleSpinnerAdapter
        toolbarRoleSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    val item = parent?.getItemAtPosition(position)
                    if (item != roles[0]) {
                        val adapter = ListAdapter(
                            applicationContext, persons.filter { it.role == item })
                        personsLV.adapter = adapter
                        adapter.notifyDataSetChanged()
                    } else {
                        val adapter = ListAdapter(applicationContext, persons)
                        personsLV.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}

            }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitBTN -> finish()
        }
        return true
    }
}