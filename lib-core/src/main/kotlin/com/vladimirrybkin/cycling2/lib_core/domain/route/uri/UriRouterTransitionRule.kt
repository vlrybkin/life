package com.vladimirrybkin.cycling2.lib_core.domain.route.uri

import android.net.Uri
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouteTransition

/**
 * A rule used by a URI router to identify the right transition handler.
 *
 * @author Vladimir Rybkin
 */
interface UriRouterTransitionRule {

    /**
     * WARNING! IT'S IMPORTANT TO CHECK ALL PARAMETERS TO IDENTIFY WHETHER THE TRANSITION HANDLER IS
     * APPLICCAPLE FOR A TRANSITION TO AVOID BLOCKING OTHER HANDLERS THAT MATCH BETTER!
     *
     * One of the keys is always not null
     */
    fun isApplicable(keyIn: Uri?,
                     keyOut: Uri?,
                     transitionIn: RouteTransition?,
                     transitionOut: RouteTransition?,
                     restored: Boolean): Boolean

}
