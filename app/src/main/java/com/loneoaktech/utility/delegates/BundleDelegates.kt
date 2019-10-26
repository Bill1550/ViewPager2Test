@file:Suppress("unused")

package com.loneoaktech.utility.delegates

import android.os.Bundle
import android.os.Parcelable
import com.loneoaktech.utility.extensions.toArrayList
import com.loneoaktech.utility.extensions.toEnumOrThrow
import kotlin.reflect.KProperty


class BundleParcelableDelegate<T: Parcelable>(private val name: String? = null)  {

    operator fun getValue(thisRef: Bundle, property: KProperty<*>): T? {
        return thisRef.getParcelable( property.key)
    }

    operator fun setValue(thisRef: Bundle, property: KProperty<*>, value: T?) {
        thisRef.putParcelable(property.key, value)
    }

    private val KProperty<*>.key: String
        get() = this@BundleParcelableDelegate.name ?: this.name
}

class BundleParcelableArrayListDelegate<T: Parcelable>(private val name: String? = null)  {

    operator fun getValue(thisRef: Bundle, property: KProperty<*>): ArrayList<T>? {
        return thisRef.getParcelableArrayList<T>( property.key)
    }

    operator fun setValue(thisRef: Bundle, property: KProperty<*>, value: ArrayList<T>?) {
        thisRef.putParcelableArrayList(property.key, value)
    }

    private val KProperty<*>.key: String
        get() = this@BundleParcelableArrayListDelegate.name ?: this.name
}

class BundleParcelableListDelegate<T: Parcelable>(private val name: String? = null)  {

    operator fun getValue(thisRef: Bundle, property: KProperty<*>): List<T>? {
        return thisRef.getParcelableArrayList( property.key)
    }

    operator fun setValue(thisRef: Bundle, property: KProperty<*>, value: List<T>?) {
        thisRef.putParcelableArrayList(property.key, value?.toArrayList())
    }

    private val KProperty<*>.key: String
        get() = this@BundleParcelableListDelegate.name ?: this.name
}


class BundleStringDelegate(private val name: String? = null)  {

    operator fun getValue(thisRef: Bundle, property: KProperty<*>): String? {
        return thisRef.getString( property.key, null)
    }

    operator fun setValue(thisRef: Bundle, property: KProperty<*>, value: String?) {
        thisRef.putString(property.key, value)
    }

    private val KProperty<*>.key: String
        get() = this@BundleStringDelegate.name ?: this.name
}

@Suppress("unused")
class BundleStringArrayListDelegate(private val name: String? = null)  {

    operator fun getValue(thisRef: Bundle, property: KProperty<*>): ArrayList<String>? {
        return thisRef.getStringArrayList( property.key )
    }

    operator fun setValue(thisRef: Bundle, property: KProperty<*>, value: ArrayList<String>?) {
        thisRef.putStringArrayList(property.key, value)
    }

    private val KProperty<*>.key: String
        get() = this@BundleStringArrayListDelegate.name ?: this.name
}

class BundleStringListDelegate(private val name: String? = null)  {

    operator fun getValue(thisRef: Bundle, property: KProperty<*>): List<String>? {
        return thisRef.getStringArrayList( property.key )
    }

    operator fun setValue(thisRef: Bundle, property: KProperty<*>, value: List<String>?) {
        thisRef.putStringArrayList(property.key, value?.toArrayList())
    }

    private val KProperty<*>.key: String
        get() = this@BundleStringListDelegate.name ?: this.name
}



class BundleBooleanDelegate(private val name: String? = null)  {

    operator fun getValue(thisRef: Bundle, property: KProperty<*>): Boolean? {
        return if (thisRef.containsKey(property.key))
            thisRef.getBoolean( property.key)
        else
            null
    }

    operator fun setValue(thisRef: Bundle, property: KProperty<*>, value: Boolean?) {
        value?.let {
            thisRef.putBoolean(property.key, value)
        } ?: thisRef.remove(property.key)
    }

    private val KProperty<*>.key: String
        get() = this@BundleBooleanDelegate.name ?: this.name
}


class BundleIntDelegate(private val name: String? = null)  {

    operator fun getValue(thisRef: Bundle, property: KProperty<*>): Int? {
        return if (thisRef.containsKey(property.key))
            thisRef.getInt( property.key)
        else
            null
    }

    operator fun setValue(thisRef: Bundle, property: KProperty<*>, value: Int?) {
        value?.let {
            thisRef.putInt(property.key, value)
        } ?: thisRef.remove(property.key)
    }

    private val KProperty<*>.key: String
        get() = this@BundleIntDelegate.name ?: this.name
}

/**
 * Convenience delegate for storing and retrieving enums to/from a bundle.
 * Uses a static creator function to allow the enum type to be reified
 * (Constructor should really be private but isn't allowed because it is call in the inline create function.
 */
class BundleEnumDelegate<E: Enum<E>>(
    private val name: String? = null,
    val decode: String.()->E?,
    val encode: E.()->String
) {
    companion object {

        inline fun <reified T: Enum<T>>create( name: String? = null) =
            BundleEnumDelegate<T>(name, { toEnumOrThrow<T>()}, { toString() } )
    }

    operator fun getValue(thisRef: Bundle, property: KProperty<*>): E? {
        return if (thisRef.containsKey(property.key))
            thisRef.getString( property.key )?.decode()
        else
            null
    }

    operator fun setValue(thisRef: Bundle, property: KProperty<*>, value: E?) {
        value?.let {
            thisRef.putString(property.key, value.encode() )
        } ?: thisRef.remove(property.key)
    }

    private val KProperty<*>.key: String
        get() = this@BundleEnumDelegate.name ?: this.name
}