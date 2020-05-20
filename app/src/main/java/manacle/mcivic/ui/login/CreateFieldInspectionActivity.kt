package manacle.mcivic.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_field_inspection.*
import manacle.mcivic.R

class CreateFieldInspectionActivity :AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_field_inspection)
        toolbar.setTitle("Field Inspection")

    }
}