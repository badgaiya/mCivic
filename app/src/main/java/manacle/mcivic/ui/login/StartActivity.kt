package manacle.mcivic.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*
import manacle.mcivic.R
import manacle.mcivic.adapter.MyStartPagerAdapter
import manacle.mcivic.fragments.StartFirstFragment
import manacle.mcivic.fragments.StartSecondFragment
import manacle.mcivic.fragments.StartThirdFragment
import manacle.mcivic.prefrences.Prefrences
import manacle.mcivic.uihelper.Uihelper
import manacle.mcivic.uilistener.ViewPagerListener

class StartActivity : AppCompatActivity() {


    companion object {
        private const val MIN_SCALE = 0.65f
        private const val MIN_ALPHA = 0.3f
    }
    private lateinit var pagerAdapterView: MyStartPagerAdapter
    private val uiHelper = Uihelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_start)

        pagerAdapterView = MyStartPagerAdapter(supportFragmentManager)
        addPagerFragments()
        myViewPager.adapter = pagerAdapterView
        myViewPager.setPageTransformer(true, this::zoomOutTransformation)
//         getStartedButton.typeface = uiHelper.getTypeFace(TypeFaceEnum.BUTTON_TEXT, this)
        myViewPager.addOnPageChangeListener(ViewPagerListener(this::onPageSelected))
        sign_up_btn.setOnClickListener( ){
            val status: String? =Prefrences.getPrefrences(this,"Signup_information")
            if (status.equals("Signup_information"))
                intent=Intent(this,SignUpTown::class.java)

                else
              intent = Intent(this, SignupActivity::class.java)



            startActivity(intent)
        }

        sign_in_btn.setOnClickListener( ){
            intent = Intent(this, SignInActivity::class.java)

            startActivity(intent)
        }
    }
    private fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                firstDotImageView.setImageResource(R.drawable.current_position_icon)
                secondDotImageView.setImageResource(R.drawable.disable_position_icon)
                thirdDotImageView.setImageResource(R.drawable.disable_position_icon)
            }
            1 -> {
                firstDotImageView.setImageResource(R.drawable.disable_position_icon)
                secondDotImageView.setImageResource(R.drawable.current_position_icon)
                thirdDotImageView.setImageResource(R.drawable.disable_position_icon)
            }
            2 -> {
                firstDotImageView.setImageResource(R.drawable.disable_position_icon)
                secondDotImageView.setImageResource(R.drawable.disable_position_icon)
                thirdDotImageView.setImageResource(R.drawable.current_position_icon)
            }
        }
    }
    private fun addPagerFragments() {
        pagerAdapterView.addFragments(StartFirstFragment())
        pagerAdapterView.addFragments(StartSecondFragment())
        pagerAdapterView.addFragments(StartThirdFragment())
    }
    private fun zoomOutTransformation(page: View, position: Float) {
        when {
            position < -1 ->
                page.alpha = 0f
            position <= 1 -> {
                page.scaleX = Math.max(MIN_SCALE, 1 - Math.abs(position))
                page.scaleY = Math.max(MIN_SCALE, 1 - Math.abs(position))
                page.alpha = Math.max(MIN_ALPHA, 1 - Math.abs(position))
            }
            else -> page.alpha = 0f
        }
    }
}