package com.example.smart_meeting.screens

import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.camera.core.Preview as CameraPreview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import java.util.concurrent.Executors
import androidx.activity.viewModels
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer

class ScannerViewModel : ViewModel() {
    var isScanning by mutableStateOf(false)
    var code = ""
}

private var viewModel: ScannerViewModel by viewModels()
private val reader = MultiFormatReader().apply {
    val map = mapOf(
        DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
    )
    setHints(map)
}

@Composable
fun ScannerScreen() {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 相机预览占据大部分空间
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        PreviewView(context).apply {
                            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                            setBackgroundColor(0x000000)
                            scaleType = PreviewView.ScaleType.FILL_START
                            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        }
                    },
                    update = { previewView ->
                        cameraProviderFuture.addListener({
                            val cameraProvider = cameraProviderFuture.get()

                            // 设置预览
                            val preview = CameraPreview.Builder().build().also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }

                            // 设置图像分析
                            val imageAnalysis = ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()
                                .apply {
                                    setAnalyzer(cameraExecutor) { imageProxy ->
                                        processImageProxy(imageProxy, viewModel)
                                    }
                                }

                            try {
                                // 解绑所有用例
                                cameraProvider.unbindAll()

                                // 绑定用例到相机
                                val camera = cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    CameraSelector.DEFAULT_BACK_CAMERA,
                                    preview,
                                    imageAnalysis
                                )

                                // 设置自动对焦
                                camera.cameraControl.enableTorch(false)
                            } catch (e: Exception) {
                                Log.e("CameraX", "相机绑定失败", e)
                            }
                        }, ContextCompat.getMainExecutor(context))
                    }
                )
            }
        }
    }
}

private fun processImageProxy(image: ImageProxy, viewModel: ScannerViewModel) {
    println("enter process!")

    if (!viewModel.isScanning) {
        image.close()
        return
    }

    try {
        // 使用更可靠的方式从ImageProxy获取图像数据
        val buffer = image.planes[0].buffer
        val data = ByteArray(buffer.remaining())
        buffer.get(data)

        // 获取图像宽高和格式
        val width = image.width
        val height = image.height

        // 使用ZXing库的PlanarYUVLuminanceSource
        val source = PlanarYUVLuminanceSource(
            data,
            width,
            height,
            0,
            0,
            width,
            height,
            false
        )

        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
        val result = reader.decode(binaryBitmap)
        println("decode success: $result")
        scanMaterialResultCallback(result.text)
    } catch (e: Exception) {
        println("decode fail: ${e.message}")
    } finally {
        image.close()
    }
}