package net.cpeek.semesterproject.db

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.VectorDrawable
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import net.cpeek.semesterproject.R
import java.io.ByteArrayOutputStream

// wow this feels so wrong not having this in a class.
// kotlin is wild yo.
fun bmpToByteArray(bmp: Bitmap): ByteArray {
    var outputStream: ByteArrayOutputStream = ByteArrayOutputStream()
    bmp.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
    return outputStream.toByteArray()
}

fun byteArrayToBmp(ba: ByteArray): Bitmap{
    return BitmapFactory.decodeByteArray(ba, 0, ba.size)
}

fun defaultImg(context: Context): Bitmap{
    //return BitmapFactory.decodeResource(context.resources, R.drawable.ic_default_team_foreground)
    val drawable = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_default_team_foreground, null) as VectorDrawable
    return drawable.toBitmap()
}

fun defaultImgByteArray(context: Context): ByteArray{
    return bmpToByteArray(defaultImg(context))
}