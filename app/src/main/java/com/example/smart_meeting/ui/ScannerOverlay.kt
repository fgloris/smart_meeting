package com.example.smart_meeting.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ScannerOverlay(
    cornerLength: Float = 40f,
    cornerStrokeWidth: Float = 8f,
    frameColor: Color = Color.White
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        
        // 扫描框的大小，设置为屏幕宽度的70%
        val frameSize = canvasWidth * 0.7f
        
        // 计算扫描框的位置，使其居中
        val frameOffset = Offset(
            x = (canvasWidth - frameSize) / 2,
            y = (canvasHeight - frameSize) / 3
        )

        // 绘制半透明的背景
        drawRect(
            color = Color.Black.copy(alpha = 0.3f),
            size = size,
            blendMode = androidx.compose.ui.graphics.BlendMode.SrcOver
        )

        // 在中间擦除一个方形区域，形成扫描框的透明部分
        drawRect(
            color = Color.White.copy(alpha = 0.2f),
            topLeft = frameOffset,
            size = Size(frameSize, frameSize),
            blendMode = androidx.compose.ui.graphics.BlendMode.SrcOver
        )

        // 左上角
        drawLine(
            color = frameColor,
            start = frameOffset,
            end = Offset(frameOffset.x + cornerLength, frameOffset.y),
            strokeWidth = cornerStrokeWidth
        )
        drawLine(
            color = frameColor,
            start = frameOffset,
            end = Offset(frameOffset.x, frameOffset.y + cornerLength),
            strokeWidth = cornerStrokeWidth
        )
        
        // 右上角
        drawLine(
            color = frameColor,
            start = Offset(frameOffset.x + frameSize, frameOffset.y),
            end = Offset(frameOffset.x + frameSize - cornerLength, frameOffset.y),
            strokeWidth = cornerStrokeWidth
        )
        drawLine(
            color = frameColor,
            start = Offset(frameOffset.x + frameSize, frameOffset.y),
            end = Offset(frameOffset.x + frameSize, frameOffset.y + cornerLength),
            strokeWidth = cornerStrokeWidth
        )
        
        // 左下角
        drawLine(
            color = frameColor,
            start = Offset(frameOffset.x, frameOffset.y + frameSize),
            end = Offset(frameOffset.x + cornerLength, frameOffset.y + frameSize),
            strokeWidth = cornerStrokeWidth
        )
        drawLine(
            color = frameColor,
            start = Offset(frameOffset.x, frameOffset.y + frameSize),
            end = Offset(frameOffset.x, frameOffset.y + frameSize - cornerLength),
            strokeWidth = cornerStrokeWidth
        )
        
        // 右下角
        drawLine(
            color = frameColor,
            start = Offset(frameOffset.x + frameSize, frameOffset.y + frameSize),
            end = Offset(frameOffset.x + frameSize - cornerLength, frameOffset.y + frameSize),
            strokeWidth = cornerStrokeWidth
        )
        drawLine(
            color = frameColor,
            start = Offset(frameOffset.x + frameSize, frameOffset.y + frameSize),
            end = Offset(frameOffset.x + frameSize, frameOffset.y + frameSize - cornerLength),
            strokeWidth = cornerStrokeWidth
        )
    }
}
