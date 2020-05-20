package manacle.mcivic.uihelper

import android.app.Activity
import android.graphics.Typeface
import manacle.mcivic.uienum.TypeFaceEnum

class Uihelper {

    fun getTypeFace(typeFaceEnum: TypeFaceEnum, activity: Activity): Typeface {
        return Typeface.createFromAsset(activity.assets, typeFaceEnum.getName())
    }
}