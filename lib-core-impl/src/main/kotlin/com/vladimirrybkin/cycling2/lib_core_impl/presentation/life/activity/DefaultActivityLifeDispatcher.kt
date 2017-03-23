package com.vladimirrybkin.cycling2.lib_core_impl.presentation.life.activity

import android.content.Intent
import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_core.presentation.activity.ActivityLifeDispatcher
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life

/**
 * The default activity life dispatcher.
 *
 * @author Vladimir Rybkin
 */
class DefaultActivityLifeDispatcher(override val life: Life,
                                    val intentToStateFunc: (Intent?) -> Bundle? = {
                                        it?.extras
                                    },
                                    val intentToResultFunc: (requestCode: Int, resultCode: Int, data: Intent) -> Bundle = {
                                        _: Int, _: Int, data: Intent -> data.extras
                                    }) : ActivityLifeDispatcher {

    override fun intentToInitialState(intent: Intent?): Bundle? {
        return intentToStateFunc(intent)
    }

    override fun intentToResult(requestCode: Int, resultCode: Int, data: Intent): Bundle {
        return intentToResultFunc(requestCode, resultCode, data)
    }

}
