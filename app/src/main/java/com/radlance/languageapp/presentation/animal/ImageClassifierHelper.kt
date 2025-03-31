package com.radlance.languageapp.presentation.animal

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import org.tensorflow.lite.task.vision.classifier.ImageClassifier.ImageClassifierOptions

/**
 * Дата создания: 31.03.2025
 * Автор: Манякин Дмитрий
 */

class ImageClassifierHelper(private val context: Context) {
    private var classifier: ImageClassifier? = null

    init {
        val options = ImageClassifierOptions.builder()
            .setMaxResults(1)
            .setScoreThreshold(0.5f)
            .build()

        classifier = ImageClassifier.createFromFileAndOptions(
            context,
            "model.tflite",
            options
        )
    }

    fun classify(image: Bitmap): String {
        val tensorImage = TensorImage.fromBitmap(image)
        val labels = context.assets.open("labels.txt").bufferedReader().readLines()

        val bestResult = classifier?.classify(tensorImage)?.firstOrNull()?.categories?.maxByOrNull {
            it.score
        }

        return bestResult?.let { labels.getOrNull(it.index) ?: "Unknown class" } ?: "Error"
    }
}