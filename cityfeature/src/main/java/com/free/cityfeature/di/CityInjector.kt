package com.free.cityfeature.di

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.free.cityfeature.CityActivity
import com.free.core.di.modules.callbacks.Injectable
import com.free.core.di.utils.InjectUtils
import com.free.layermodularization.MainApplication
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

class CityInjector {
    companion object {

        fun init(activity: CityActivity) {
            DaggerCityComponent.builder()
                .coreComponent(InjectUtils.provideCoreComponent(activity.applicationContext))
                .cityActivity(activity = activity)
                .build()
                .inject(activity)

//            app.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallback() {
//                override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
//                    super.onActivityCreated(activity, bundle)
//                    handleActivity(activity)
//                }
//
//            })
        }


        //Handle fragments injection on entry into activity

        fun handleActivity(activity: Activity) {

            (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {

                    override fun onFragmentPreAttached(
                        fm: FragmentManager,
                        f: Fragment,
                        context: Context
                    ) {
                        super.onFragmentPreAttached(fm, f, context)

                        if (f is Injectable) {
                            AndroidSupportInjection.inject(f)
                        }
                    }
                }, true
            )

        }
    }
}