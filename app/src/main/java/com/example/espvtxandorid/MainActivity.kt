package com.example.espvtxandorid

import android.graphics.ImageDecoder
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.nio.ByteBuffer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = findViewById<TextView>(R.id.textview)
        val imageview = findViewById<ImageView>(R.id.imageView)
        val mainExecutor = ContextCompat.getMainExecutor(this)
        println("hello,world!")
        Thread{

            val socket = DatagramSocket(InetSocketAddress("0.0.0.0", 12345) )
            //socket.connect(InetSocketAddress(12345))
            val buffer = ByteArray(65535)
            val bufferWrapper = ByteBuffer.wrap(buffer)
            text.text = "Socket Inited"
            while(true){
                val packet = DatagramPacket(buffer,buffer.size)
                socket.receive(packet)

                bufferWrapper.limit(packet.length)
                val source = ImageDecoder.createSource(bufferWrapper)
                val drawable =  ImageDecoder.decodeDrawable(source)

                mainExecutor.execute {
                    // You code logic goes here.
                    imageview.setImageDrawable(drawable)
                }

            }
        }.start()

    }
}