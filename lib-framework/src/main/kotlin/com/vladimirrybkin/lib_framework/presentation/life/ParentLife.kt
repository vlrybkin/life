package com.vladimirrybkin.lib_framework.presentation.life

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.LifeWrapper

/**
 * A life wrapper with parent layout loading.
 *
 * @author Vladimir Rybkin
 */
open class ParentLife(life: Life): LifeWrapper(life) {

    override fun onCreateView(parentViewGroup: ViewGroup, inState: Bundle?) {
        val annotation = life.javaClass.getAnnotation(ParentLayout::class.java)
        if (annotation == null) {
            super.onCreateView(parentViewGroup, inState)
        } else {
            val context = parentViewGroup.context
            val wrapView = LayoutInflater.from(context).inflate(
                    annotation.layoutId, parentViewGroup, false) as ViewGroup
            val lifeView = if (annotation.rootViewId > 0)
                wrapView.findViewById(annotation.rootViewId) as ViewGroup
            else
                wrapView
            parentViewGroup.addView(wrapView)
            super.onCreateView(lifeView, inState)
        }
    }

}
