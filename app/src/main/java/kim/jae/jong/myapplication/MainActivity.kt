package kim.jae.jong.myapplication

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var galleray: Button
    private lateinit var drawButton: Button


    private val launcher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result: Uri? ->
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
            drawCanvas()
        }

    }

    private fun drawCanvas() {
        val paintLine = Paint().apply {
            strokeWidth = 1F
            color = Color.RED
            style = Paint.Style.FILL
        }

        val bitmap = (image.drawable as BitmapDrawable).bitmap

        //Create a new image bitmap and attach a brand new canvas to it
        val tempBitmap =
            Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888)
        val tempCanvas = Canvas(tempBitmap)

//Draw the image bitmap into the cavas
        tempCanvas.drawBitmap(bitmap, 0F, 0F, null)

//Draw everything else you want into the canvas, in this example a rectangle with rounded edges
        tempCanvas.drawRect(100F, 100F, 500F, 500F, paintLine)

//Attach the canvas to the ImageView
        image.setImageDrawable(BitmapDrawable(resources, tempBitmap))
    }
}

/*
    fun openVideoFile() {
        val videoFile: File
        val videoFileUri: Uri
        val retriever: MediaMetadataRetriever
        val bitmapArrayList: ArrayList<Bitmap>
        val mediaPlayer: MediaPlayer
        var bitmap: Bitmap?
        var thread: Thread?
        // test.mp4 동영상파일을 불러온다.
        videoFile = File(Environment.getExternalStorageDirectory().toString() + "/49.mp4")
        videoFileUri = Uri.parse(videoFile.toString())
        // instance 생성
        retriever = MediaMetadataRetriever()
        // 추출할 bitmap 을 담을 array 생성
        bitmapArrayList = ArrayList<Bitmap>()
        // 사용할 data source의 경로를 설정
        retriever.setDataSource(videoFile.toString())
        // video file의 총 재생시간을 얻어오기위함
        mediaPlayer = MediaPlayer.create(baseContext, videoFileUri)
        // 3초 짜리 동영상을 사용. millisecond = 3000
        val millisecond: Int = mediaPlayer.getDuration()
        // 1000씩 증가를 시킨 이유는 총 3초짜리 동영상 을 1초마다 bitmap을 얻고 싶어서.
        var i = 0
        while (i < millisecond) {

            // getFrameAtTime 함수는 i 라는 타임에 bitmap을 얻는다.
            // getFrameAtTime의 첫번째 인자의 unit 은 microsecond이다.
            bitmap = retriever.getFrameAtTime((i * 1000).toLong(), MediaMetadataRetriever.OPTION_CLOSEST)
            bitmapArrayList.add(bitmap!!)
            i += 1000
        }
        // retreiver를 모두 사용하면 release 한다.
        retriever.release()

        // Thread start
        thread = Thread {
            try {
                saveFrames(bitmapArrayList)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    @Throws(IOException::class)
    fun saveFrames(saveBitmap: ArrayList<Bitmap>) {
        val folder = Environment.getExternalStorageDirectory().toString()
        val saveFolder = File("$folder/")
        if (!saveFolder.exists()) {
            saveFolder.mkdirs()
        }
        var i = 1
        for (bmp in saveBitmap) {
            val bytes = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes)
            val file = File(saveFolder, "filename$i.jpg")
            file.createNewFile()
            val fileOutStream = FileOutputStream(file)
            fileOutStream.write(bytes.toByteArray())
            fileOutStream.flush()
            fileOutStream.close()
            i++
        }
        thread.interrupt()
    }

 */