package com.vladimirrybkin.cycling2.lib_core.domain.route.base

/**
 * Router actions.
 *
 * @author Vladimir Rybkin
 */
enum class RouterAction {

    ACTION_PUSH {
        override fun actionName(): String = "push"
    },
    ACTION_REPLACE_TOP {
        override fun actionName(): String = "replaceTop"
    },
    ACTION_POP {
        override fun actionName(): String = "pop"
    },
    ACTION_RESTART_STACK {
        override fun actionName(): String = "restartStack"
    };

    abstract fun actionName(): String

}
