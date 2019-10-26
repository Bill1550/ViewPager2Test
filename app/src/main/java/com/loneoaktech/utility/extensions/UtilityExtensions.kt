
@file:Suppress("unused")
package com.loneoaktech.utility.extensions

import java.util.concurrent.atomic.AtomicBoolean

/**
 * Some extra helpers, general purpose
 */

/**
 * return false if the receiver is false, otherwise null
 */
fun Boolean.falseOrNull(): Boolean? = if (!this) false else null

/**
 * return true if the receiver is true, otherwise null
 */
fun Boolean.trueOrNull(): Boolean? = if (this) true else null

/**
 * run the supplied function if the receiver is true.
 *
 * Either way return the value of the receiver.
 */
fun Boolean.applyIfTrue(predicate: () -> Unit): Boolean {
    if (this) predicate.invoke()
    return this
}


fun Int.nonZeroOrNull(): Int? = if (this != 0) this else null

/**
 * Runs the specified test on the subject, if true, returns the subject,
 * if false, return null
 */
fun <T> T.nullIfFalse(predicate: (T) -> Boolean): T? =
    if (predicate(this)) this else null

/**
 * Runs the specified test on the subject, if false, returns the subject,
 * if false, return null
 */
fun <T> T.nullIfTrue(predicate: (T) -> Boolean): T? =
    if (predicate(this).not()) this else null


/**
 *  Executed the supplied handler if the receiver is true.
 *  Returns the value of the receiver.
 */
fun Boolean.applyIf( handler: () -> Unit): Boolean {
    if (this) handler()
    return this
}

/**
 * Removes all keys from a map and returns the number removed.
 */
fun <K,V> MutableMap<K,V>.removeAll(keys: Iterable<K>): Int{
    var numRemoved = 0
    for(k in keys)
        if (this.containsKey(k)) {
            this.remove(k)
            numRemoved++
        }

    return numRemoved
}



fun AtomicBoolean.setAndExecuteIfChanged(newValue: Boolean, handler: (Boolean)->Unit) {
    val oldValue = this.getAndSet(newValue)
    if (oldValue != newValue)
        handler.invoke(newValue)
}



/**
 * returns the enum specified by the name, or the default if the name is not recognized.
 */
inline fun <reified T : Enum<T>> safeEnumValueOf(name: String?, default: T): T =
    name?.let {
        try {
            java.lang.Enum.valueOf(T::class.java, name)
        } catch (e: Throwable) {
            default
        }
    } ?: default



inline fun <reified  E: kotlin.Enum<E>> String.toEnumOrNull(): E? =
    try {
        java.lang.Enum.valueOf(E::class.java, this)
    } catch (t: Throwable ){
        null
    }
//
//enum class Test { ONE, TWO }
//

inline fun <reified E: kotlin.Enum<E>> String.toEnumOrThrow(): E =
    java.lang.Enum.valueOf(E::class.java, this)


/**
 * Creates a string from the map of the form: key0=value0, key1=value1
 */
fun Map<String,Any?>.toCsvString(): String =
    entries.joinToString(", ") { entry ->
        entry.key + "=" + entry.value?.toString()
    }


/**
 * Converts a generic list to an ArrayList
 */
fun <T> List<T>.toArrayList(): ArrayList<T> =
    arrayListOf<T>().apply { addAll(this@toArrayList ) }


/**
 * Casts a reference if it can. A little simpler syntax.
 */
inline fun <reified T> Any?.castTo(): T? = (this as? T)


