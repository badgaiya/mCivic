package manacle.mcivic.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_complaint_activity.*
import manacle.mcivic.R


class CreateComplaintActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_complaint_activity)
        toolbar.setTitle("Complaint")
    }
}