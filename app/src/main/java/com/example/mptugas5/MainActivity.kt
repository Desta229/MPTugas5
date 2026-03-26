package com.example.mptugas5

import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.mptugas5.R

class MainActivity : AppCompatActivity() {

    lateinit var tilNama: TextInputLayout
    lateinit var tilEmail: TextInputLayout
    lateinit var tilPassword: TextInputLayout
    lateinit var tilConfirm: TextInputLayout

    lateinit var etNama: TextInputEditText
    lateinit var etEmail: TextInputEditText
    lateinit var etPassword: TextInputEditText
    lateinit var etConfirm: TextInputEditText

    lateinit var rgGender: RadioGroup
    lateinit var cb1: CheckBox
    lateinit var cb2: CheckBox
    lateinit var cb3: CheckBox
    lateinit var cb4: CheckBox

    lateinit var acProgramStudi: AutoCompleteTextView
    lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // INIT VIEW
        tilNama = findViewById(R.id.tilNama)
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        tilConfirm = findViewById(R.id.tilConfirm)

        etNama = findViewById(R.id.etNama)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirm = findViewById(R.id.etConfirm)

        rgGender = findViewById(R.id.rgGender)

        cb1 = findViewById(R.id.cb1)
        cb2 = findViewById(R.id.cb2)
        cb3 = findViewById(R.id.cb3)
        cb4 = findViewById(R.id.cb4)

        acProgramStudi = findViewById(R.id.acProgramStudi)
        btnSubmit = findViewById(R.id.btnSubmit)

        // DROPDOWN DATA
        val data = arrayOf("Informatika", "Sistem Informasi", "DKV")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            data
        )
        acProgramStudi.setAdapter(adapter)

        // CLICK SUBMIT
        btnSubmit.setOnClickListener {
            validate()
        }

        // LONG PRESS
        btnSubmit.setOnLongClickListener {
            Toast.makeText(this, "Long Press Aktif!", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun validate() {

        // Menambahkan .trim() untuk menghapus spasi yang tidak sengaja terketik di awal/akhir
        val nama = etNama.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val pass = etPassword.text.toString().trim()
        val confirm = etConfirm.text.toString().trim()
        val prodi = acProgramStudi.text.toString().trim()

        // RESET ERROR
        tilNama.error = null
        tilEmail.error = null
        tilPassword.error = null
        tilConfirm.error = null

        if (nama.isEmpty()) {
            tilNama.error = "Nama wajib diisi"
            return
        }

        // Validasi format email secara umum
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = "Email tidak valid"
            return
        }

        // Validasi khusus untuk memastikan menggunakan @gmail.com dengan benar
        if (!email.endsWith("@gmail.com")) {
            tilEmail.error = "Email harus diakhiri dengan @gmail.com"
            return
        }

        if (pass.length < 6) {
            tilPassword.error = "Password minimal 6 karakter"
            return
        }

        if (pass != confirm) {
            tilConfirm.error = "Password tidak sama"
            return
        }

        // RADIO
        if (rgGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Pilih gender", Toast.LENGTH_SHORT).show()
            return
        }

        // CHECKBOX
        var count = 0
        if (cb1.isChecked) count++
        if (cb2.isChecked) count++
        if (cb3.isChecked) count++
        if (cb4.isChecked) count++

        if (count < 3) {
            Toast.makeText(this, "Pilih minimal 3 hobi", Toast.LENGTH_SHORT).show()
            return
        }

        // PRODI
        if (prodi.isEmpty()) {
            Toast.makeText(this, "Pilih Program Studi", Toast.LENGTH_SHORT).show()
            return
        }

        showDialog()
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah data sudah benar?")
            .setPositiveButton("Ya") { _, _ ->
                Toast.makeText(this, "Data berhasil dikirim!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
}
