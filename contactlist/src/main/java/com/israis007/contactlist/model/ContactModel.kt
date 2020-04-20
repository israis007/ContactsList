package com.israis007.contactlist.model

import android.graphics.drawable.Drawable
import android.view.View

data class ContactModel(
    var id: Int,
    var name: String,
    var info: String?,
    var info_view: View?,
    var isfavorite: Boolean,
    var iconDrawable: Drawable?,
    var iconURL: String?,
    var any: Any?
) {

    constructor(name: String, info: String, isfavorite: Boolean, iconDrawable: Drawable) : this(
        id = View.generateViewId(),
        name = name,
        info = info,
        info_view = null,
        isfavorite = isfavorite,
        iconDrawable = iconDrawable,
        iconURL = null,
        any = null
    )

    constructor(name: String, info: String, isfavorite: Boolean, iconURL: String) : this(
        id = View.generateViewId(),
        name = name,
        info = info,
        info_view = null,
        isfavorite = isfavorite,
        iconDrawable = null,
        iconURL = iconURL,
        any = null
    )

    constructor(
        name: String,
        info: String,
        isfavorite: Boolean,
        iconDrawable: Drawable,
        any: Any
    ) : this(
        id = View.generateViewId(),
        name = name,
        info = info,
        info_view = null,
        isfavorite = isfavorite,
        iconDrawable = iconDrawable,
        iconURL = null,
        any = any
    )

    constructor(name: String, info: String, isfavorite: Boolean, iconURL: String, any: Any) : this(
        id = View.generateViewId(),
        name = name,
        info = info,
        info_view = null,
        isfavorite = isfavorite,
        iconDrawable = null,
        iconURL = iconURL,
        any = any
    )

    constructor(name: String, info: String, iconDrawable: Drawable) : this(
        id = View.generateViewId(),
        name = name,
        info = info,
        info_view = null,
        isfavorite = false,
        iconDrawable = iconDrawable,
        iconURL = null,
        any = null
    )

    constructor(name: String, info: String, iconURL: String) : this(
        id = View.generateViewId(),
        name = name,
        info = info,
        info_view = null,
        isfavorite = false,
        iconDrawable = null,
        iconURL = iconURL,
        any = null
    )

    constructor(name: String, info: String, iconDrawable: Drawable, any: Any) : this(
        id = View.generateViewId(),
        name = name,
        info = info,
        info_view = null,
        isfavorite = false,
        iconDrawable = iconDrawable,
        iconURL = null,
        any = any
    )

    constructor(name: String, info: String, iconURL: String, any: Any) : this(
        id = View.generateViewId(),
        name = name,
        info = info,
        info_view = null,
        isfavorite = false,
        iconDrawable = null,
        iconURL = iconURL,
        any = any
    )

    /* Custom View */

    constructor(name: String, info_view: View, isfavorite: Boolean, iconDrawable: Drawable) : this(
        id = View.generateViewId(),
        name = name,
        info = null,
        info_view = info_view,
        isfavorite = isfavorite,
        iconDrawable = iconDrawable,
        iconURL = null,
        any = null
    )

    constructor(name: String, info_view: View, isfavorite: Boolean, iconURL: String) : this(
        id = View.generateViewId(),
        name = name,
        info = null,
        info_view = info_view,
        isfavorite = isfavorite,
        iconDrawable = null,
        iconURL = iconURL,
        any = null
    )

    constructor(
        name: String,
        info_view: View,
        isfavorite: Boolean,
        iconDrawable: Drawable,
        any: Any
    ) : this(
        id = View.generateViewId(),
        name = name,
        info = null,
        info_view = info_view,
        isfavorite = isfavorite,
        iconDrawable = iconDrawable,
        iconURL = null,
        any = any
    )

    constructor(name: String, info_view: View, isfavorite: Boolean, iconURL: String, any: Any) : this(
        id = View.generateViewId(),
        name = name,
        info = null,
        info_view = info_view,
        isfavorite = isfavorite,
        iconDrawable = null,
        iconURL = iconURL,
        any = any
    )

    constructor(name: String, info_view: View, iconDrawable: Drawable) : this(
        id = View.generateViewId(),
        name = name,
        info = null,
        info_view = info_view,
        isfavorite = false,
        iconDrawable = iconDrawable,
        iconURL = null,
        any = null
    )

    constructor(name: String, info_view: View, iconURL: String) : this(
        id = View.generateViewId(),
        name = name,
        info = null,
        info_view = info_view,
        isfavorite = false,
        iconDrawable = null,
        iconURL = iconURL,
        any = null
    )

    constructor(name: String, info_view: View, iconDrawable: Drawable, any: Any) : this(
        id = View.generateViewId(),
        name = name,
        info = null,
        info_view = info_view,
        isfavorite = false,
        iconDrawable = iconDrawable,
        iconURL = null,
        any = any
    )

    constructor(name: String, info_view: View, iconURL: String, any: Any) : this(
        id = View.generateViewId(),
        name = name,
        info = null,
        info_view = info_view,
        isfavorite = false,
        iconDrawable = null,
        iconURL = iconURL,
        any = any
    )

}