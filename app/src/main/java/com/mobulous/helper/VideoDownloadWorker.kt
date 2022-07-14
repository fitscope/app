package com.mobulous.helper

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class VideoDownloadWorker(val ctx: Context, val params: WorkerParameters) : Worker(ctx, params) {
    var offlineUrl = ""
    var enrollImage = ""
    lateinit var mVideoObj: String
    override fun doWork(): Result {
        try {

            var input: InputStream? = null
            var output: OutputStream? = null
            var connection: HttpURLConnection? = null
            try {
                enrollImage = inputData.getString(Enums.EnrollImageUrl.toString()) ?: ""
                mVideoObj = inputData.getString(Enums.Obj.toString()) ?: ""
                val url = URL(inputData.getString("videoUrl"))
//                val url =
//                    URL("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")

                connection = url.openConnection() as HttpURLConnection
                connection.connect()

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
//                if (connection!!.responseCode != HttpURLConnection.HTTP_OK) {
//                    return ("Server returned HTTP " + connection.responseCode
//                            + " " + connection.responseMessage)
//                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                val fileLength = connection.contentLength

                // download the file
                input = connection.inputStream

//            output = new FileOutputStream("/data/data/com.example.vadym.test1/textfile.txt");
                output =
                    FileOutputStream(ctx.filesDir.toString() + "/${inputData.getString("ChapterID")}.mp4")
                offlineUrl = ctx.filesDir.toString() + "/${inputData.getString("ChapterID")}.mp4"

                val data = ByteArray(4096)
                var total: Long = 0
                var count: Int
                while (input.read(data).also { count = it } != -1) {
                    // allow canceling with back button
//                    if (isCancelled) {
//                        input.close()
//                        return null
//                    }
                    total += count.toLong()
                    // publishing the progress....
                    // only if total length is known
                    if (fileLength > 0) {
                        setProgressAsync(workDataOf("Progress" to (total * 100 / fileLength).toInt()))
                        // progressLisntr.onProgress((total * 100 / fileLength).toInt())
//                        println("---progress----->${(total * 100 / fileLength).toInt()}")
                    }

                    output.write(data, 0, count)
                }
            } catch (e: java.lang.Exception) {
                return Result.failure()
            } finally {
                try {
                    output?.close()
                    input?.close()
                } catch (ignored: IOException) {
                }
                connection?.disconnect()
            }


        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }


        return Result.success(
            Data.Builder()
                .putString("OfflineUrl", offlineUrl)
                .putString(Enums.Obj.toString(), mVideoObj)
                .putString(Enums.EnrollImageUrl.toString(), enrollImage)
                .build()
        )
    }

}