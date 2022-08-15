package kim.jae.jong.myapplication

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var galleray: Button
    private lateinit var drawButton: Button


    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { result: Uri? ->
        Glide.with(image).load(result).into(image)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.imageView)
        galleray = findViewById(R.id.galleray)

        drawButton = findViewById(R.id.drawButton)

        image.setOnClickListener {
            launcher.launch("image/*")
        }

        drawButton.setOnClickListener {
            drawCanvas ()
        }

    }

    private fun drawCanvas (){
        val paintLine = Paint().apply {
            strokeWidth = 1F
            color = Color.RED
            style = Paint.Style.FILL
        }

        val bitmap = ( image.drawable as BitmapDrawable).bitmap

        //Create a new image bitmap and attach a brand new canvas to it
        val tempBitmap =  Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888)
        val tempCanvas = Canvas(tempBitmap)

//Draw the image bitmap into the cavas
        tempCanvas.drawBitmap(bitmap, 0F, 0F , null)

//Draw everything else you want into the canvas, in this example a rectangle with rounded edges
        tempCanvas.drawRect(100F, 100F, 500F, 500F, paintLine)

//Attach the canvas to the ImageView
        image.setImageDrawable(BitmapDrawable(resources, tempBitmap))
    }
}