package manacle.mcivic.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_helper_data_form.*
import manacle.mcivic.R
import manacle.mcivic.api.ApiClient
import manacle.mcivic.api.ApiInterface
import manacle.mcivic.common.Constants
import manacle.mcivic.data.model.MobileExist
import manacle.mcivic.data.model.SpinnerListGender
import manacle.mcivic.data.model.SpinnerListId
import manacle.mcivic.prefrences.Prefrences
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


class HelperDataForm : AppCompatActivity() {

    // private lateinit var mImageView: AppCompatImageView
    //lateinit var mediaFile: File

    private var selectedImage: Uri? = null
    lateinit var actv: AutoCompleteTextView

    //Our widgets
    lateinit var spinList: ArrayList<SpinnerListId>


    lateinit private var gender_spinner_list: ArrayList<SpinnerListGender>

    lateinit var dataExits: ArrayList<MobileExist>


    // private val parents: ArrayList<ParentItem> = ArrayList()


    var id_card_id: String = ""
    var gender_id: String = ""
    private var reg_no = ""
    private var role_id = ""
    private var login_id = ""
    private var existmsg = ""
    lateinit var progerssProgressDialog: ProgressDialog
    var company_id: String = ""
    var MobilePattern = "[0-9]{10}"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helper_data_form)
        reg_no = Constants.orderId()
        role_id = Prefrences.getPrefrences(this, "role_id")!!

        mobie_no.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


          if (mobie_no.text.toString().length === 10) {
              mobileExist(mobie_no)
              while (existmsg == null) {

              }
              if (existmsg != null && existmsg.equals("exist")) {

                  Toast.makeText(
                      applicationContext,
                      "mobile number already exist please login", Toast.LENGTH_LONG
                  ).show()

              } else if (existmsg != null && existmsg.equals("notExist")) {
                  submitData()

              }
          }


            }
        })



        initView()
        btnCapture.setOnClickListener({
            selectImage(this)
        })
        getProductData()
    }
    private fun selectImage(context: Context) {
        val options =
            arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Choose your profile picture")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            if (options[item] == "Take Photo") {
                val takePicture =
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, 0)
            } else if (options[item] == "Choose from Gallery") {
                val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(pickPhoto, 1)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        })
        builder.show()
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == Activity.RESULT_OK && data != null) {
                    val selectedImage = data.extras!!["data"] as Bitmap?
                    this.mImageView.setImageBitmap(selectedImage)
                }
                1 -> if (resultCode == Activity.RESULT_OK && data != null) {
                    selectedImage = data.data
                    val im =
                        arrayOf(MediaStore.Images.Media.DATA)
                    if (selectedImage != null) {
                        val cursor: Cursor? = contentResolver.query(
                            selectedImage!!,
                            im, null, null, null
                        )
                        if (cursor != null) {
                            cursor.moveToFirst()
                            val columnIndex: Int = cursor.getColumnIndex(im[0])
                            val picturePath: String = cursor.getString(columnIndex)
                            mImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                            cursor.close()
                        }
                    }
                }
            }
        }
    }
    /* private fun isValidPhoneNumber(mobie_no: CharSequence): Boolean {
         return if (!TextUtils.isEmpty(mobie_no.toString())) {
             Patterns.PHONE.matcher(mobie_no.toString()).matches()
         } else false
     }*/
    private fun initView() {
        val fab: ExtendedFloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            submitData()
        }
    }
    private fun mobileExist(mobieNo: AppCompatEditText) {
        val mobie_no: String = mobie_no.text.toString()
        try {
            //  val jsonArray: JsonArray = JsonArray()

            val service = ApiClient.getClient().create(ApiInterface::class.java)

            val call = service.getMobileExist("37", mobie_no)

            call.enqueue(object : Callback<JsonObject> {

                override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {


                    try {
                        progerssProgressDialog = ProgressDialog(this@HelperDataForm)
                        progerssProgressDialog.setTitle("Please wait for proceeding!!")
                        progerssProgressDialog.setCancelable(false)
                        progerssProgressDialog.show()

                        if (response != null) {
                            if (response.code() == 200) {
                                Log.d("qwer", response.toString())

                                val mResponseBody: JsonObject = response.body()!!
                                val responseStatus: Boolean =
                                    mResponseBody.get("response").getAsBoolean()

                                if (responseStatus) {
                                    val data: JsonArray =
                                        mResponseBody.getAsJsonObject().getAsJsonArray("data")

                                    dataExits = ArrayList()
                                    dataExits.clear()
                                    for (j in 0 until data!!.size()) {
                                        val name: String =
                                            data.get(j)
                                                .asJsonObject.get("fullname").asString
                                        val id_card_no: String =
                                            data.get(j)
                                                .asJsonObject.get("id_card_no").asString
                                        val father_: String =
                                            data.get(j)
                                                .asJsonObject.get("father_name").asString
                                        val addr_ess: String =
                                            data.get(j)
                                                .asJsonObject.get("address").asString
                                        val id_card_id: String =
                                            data.get(j)
                                                .asJsonObject.get("id_card_id").asString
                                        val gender_spinner_list: String =
                                            data.get(j)
                                                .asJsonObject.get("gender").asString
                                        name_et.setText(name);
                                        person_id.setText(id_card_no);
                                        father_name.setText(father_);
                                        dob.setText(name);
                                        address.setText(addr_ess);
                                        // severity_spinner.get(id_card_id.toInt())
                                        // gender_spinner.get(gender_spinner_list.toInt())
                                    }


                                /*    val spinnerArrayy =
                                        arrayOfNulls<String>(dataExits.size)

                                    val spinnerMapp: HashMap<Int, String> = HashMap<Int, String>();
                                    for (i in 0 until dataExits.size) {
                                        spinnerMapp.put(i, dataExits.get(i).id);
                                        spinnerArrayy[i] = dataExits.get(i).fullname;
                                    }
                                    val adapter: ArrayAdapter<String> =
                                        ArrayAdapter<String>(
                                            this@HelperDataForm,
                                            android.R.layout.simple_spinner_item,
                                            spinnerArrayy
                                        );

                                    name_et.setThreshold(1)
                                    name_et.setAdapter(adapter);*/

                                    progerssProgressDialog.dismiss()








                                }


                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    progerssProgressDialog.dismiss()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    private fun submitData() {
        val id_card_no: String = person_id.text.toString()
        val name: String = name_et.text.toString()
        val mobie_no: String = mobie_no.text.toString()
        val father_name: String = father_name.text.toString()
        val dob: String = dob.text.toString()
        val address: String = address.text.toString()
        var imageBitmap: Bitmap? = null
        var _shrinkImageFile: File? = null
        var image_source: MultipartBody.Part
        var card_image: MultipartBody.Part
        try {
            _shrinkImageFile = File(selectedImage.toString())
            imageBitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeFile(_shrinkImageFile!!.path),
                768,
                1024,
                false
            )
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, FileOutputStream(_shrinkImageFile))
            image_source = MultipartBody.Part.createFormData(
                "vendor_image",
                _shrinkImageFile!!.name,
                RequestBody.create(MediaType.parse("multipart/form-data"), _shrinkImageFile)
            )
            card_image = MultipartBody.Part.createFormData(
                "card_image",
                _shrinkImageFile!!.name,
                RequestBody.create(MediaType.parse("multipart/form-data"), _shrinkImageFile)
            )
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            image_source = MultipartBody.Part.createFormData(
                "vendor_image",
                "",
                RequestBody.create(MediaType.parse("multipart/form-data"), "")
            )
            card_image = MultipartBody.Part.createFormData(
                "card_image",
                "",
                RequestBody.create(MediaType.parse("multipart/form-data"), "")
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            image_source = MultipartBody.Part.createFormData(
                "vendor_image",
                "",
                RequestBody.create(MediaType.parse("multipart/form-data"), "")
            )
            card_image = MultipartBody.Part.createFormData(
                "card_image",
                "",
                RequestBody.create(MediaType.parse("multipart/form-data"), "")
            )
        }
        try {
            progerssProgressDialog = ProgressDialog(this)
            progerssProgressDialog.setTitle("Please wait for proceeding!!")
            progerssProgressDialog.setCancelable(false)
            progerssProgressDialog.show()
            val service = ApiClient.getClient().create(ApiInterface::class.java)
            val call = service.sendVendorData("37",
                reg_no, name, mobie_no, address, role_id, id_card_id, gender_id,
                Constants.getDate(), father_name, "0", id_card_no, image_source, card_image)
            call.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                    try {
                        if (response != null) {
                            if (response.code() == 200) {
                                Log.d("asdfghj", response.toString())
                                progerssProgressDialog.dismiss()
                                intent = Intent(this@HelperDataForm, MyHelper::class.java)
                                intent.putExtra("mobile", mobie_no)
                                //  intent.putExtra("mobile", mobie_no.getText().toString())
                                startActivity(intent)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    progerssProgressDialog.dismiss()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun getProductData() {
        val service = ApiClient.getClient().create(ApiInterface::class.java)
        val call = service.myHelperDataList("37")
        call.enqueue(object : Callback<JsonObject> {
            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                try {
                    if (response != null) {
                        if (response.code() == 200) {
                            Log.d("response____________", response.toString())
                            /* childList = ArrayList()
                             childList.clear()*/
                            spinList = ArrayList()
                            spinList.clear()
                            gender_spinner_list = ArrayList()
                            gender_spinner_list.clear()
                            val mResponseBody: JsonObject = response.body()!!
                            val responseStatus: Boolean =
                                mResponseBody.get("response").getAsBoolean()
                            if (responseStatus) {
                                val payment_mode: JsonArray =
                                    mResponseBody.getAsJsonObject()
                                        .getAsJsonArray("id_proof")
                                if (payment_mode.size() > 0) {
                                    for (j in 0 until payment_mode!!.size()) {
                                        val id: String =
                                            payment_mode.get(j)
                                                .asJsonObject.get("id").asString
                                        val name: String =
                                            payment_mode.get(j)
                                                .asJsonObject.get("name").asString
                                        var childItem = SpinnerListId(
                                            id,
                                            name
                                        )
                                        spinList.add(childItem)
                                    }
                                }
                                val spinnerArray = arrayOfNulls<String>(spinList.size)
                                val spinnerMap: HashMap<Int, String> = HashMap<Int, String>();
                                for (i in 0 until spinList.size) {
                                    spinnerMap.put(i, spinList.get(i).id);
                                    spinnerArray[i] = spinList.get(i).name;
                                }
                                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                    this@HelperDataForm, android.R.layout.simple_spinner_item,
                                    spinnerArray
                                );
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                severity_spinner.setAdapter(adapter);
                                severity_spinner.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            arg0: AdapterView<*>?,
                                            arg1: View,
                                            arg2: Int,
                                            arg3: Long
                                        ) {
                                            id_card_id = spinnerMap.get(severity_spinner.getSelectedItemPosition())
                                                    .toString();
                                        }
                                        override fun onNothingSelected(arg0: AdapterView<*>?) {}
                                    }
                                val gender_type: JsonArray =
                                    mResponseBody.getAsJsonObject()
                                        .getAsJsonArray("gender")
                                if (gender_type.size() > 0) {
                                    for (j in 0 until gender_type!!.size()) {
                                        val id: String =
                                            gender_type.get(j)
                                                .asJsonObject.get("id").asString
                                        val name: String =
                                            gender_type.get(j)
                                                .asJsonObject.get("name").asString
                                        var childItem = SpinnerListGender(
                                            id,
                                            name
                                        )
                                        gender_spinner_list.add(childItem)

                                    }
                                    val spinnerArrayy =
                                        arrayOfNulls<String>(gender_spinner_list.size)

                                    val spinnerMapp: HashMap<Int, String> = HashMap<Int, String>();
                                    for (i in 0 until gender_spinner_list.size) {
                                        spinnerMapp.put(i, gender_spinner_list.get(i).id);
                                        spinnerArrayy[i] = gender_spinner_list.get(i).name;
                                    }
                                    val adapterr: ArrayAdapter<String> =
                                        ArrayAdapter<String>(
                                            this@HelperDataForm,
                                            android.R.layout.simple_spinner_item,
                                            spinnerArrayy
                                        );
                                    adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    gender_spinner.setAdapter(adapterr);
                                    gender_spinner.onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(
                                                arg0: AdapterView<*>?,
                                                arg1: View,
                                                arg2: Int,
                                                arg3: Long
                                            ) {
                                                gender_id =
                                                    spinnerMapp.get(gender_spinner.getSelectedItemPosition())
                                                        .toString();
                                            }

                                            override fun onNothingSelected(arg0: AdapterView<*>?) {}
                                        }

                                } else {

                                }


                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()


                }


            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
            }

        })
    }

}

