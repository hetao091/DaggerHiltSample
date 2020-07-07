package tt.reducto.daggerhiltsample.utils

import android.app.Activity

import kotlin.properties.ReadOnlyProperty

import kotlin.reflect.KProperty

/**
 * ……。
 *
 * <p>......。</p>
 * <ul><li></li></ul>
 * <br>
 * <strong>Time</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020/5/18 12:38<br>
 * <strong>CopyRight</strong>&nbsp;&nbsp;&nbsp;&nbsp;2020, tt.reducto<br>
 *
 * @version  : 1.0.0
 * @author   : hetao
 */
fun <T> Activity.viewBinding(initialise: () -> T): ReadOnlyProperty<Activity, T> = object :
    ReadOnlyProperty<Activity, T> {

    private var binding: T? = null


    /**
     * Returns the value of the property for the given object.
     * @param thisRef the object for which the value is requested.
     * @param property the metadata for the property.
     * @return the property value.
     */
    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        return this.binding ?: initialise().also {
            this.binding = it
        }
    }

}