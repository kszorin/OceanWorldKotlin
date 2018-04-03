package ru.kszorin.seaworldkotlin.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import ru.kszorin.seaworldkotlin.entities.Creature
import ru.kszorin.seaworldkotlin.entities.World
import ru.kszorin.seaworldkotlin.use_cases.dto.CreatureStepData
import rx.Completable
import rx.Subscription
import rx.functions.Action0
import rx.schedulers.Schedulers

/**
 * Created on 28.03.2018.
 */
class PlayingWorldView(context: Context, attrs: AttributeSet)
    : SurfaceView(context, attrs), SurfaceHolder.Callback {

    //region field
    var fieldSizeX = 0
    var fieldSizeY = 0

    private var screenWidth = 0
    private var screenHeight = 0
    private var squareWidth = 0F
    private var squareHeight = 0F

    //endregion

    //region drawing
    private val orcaBmp = BitmapFactory.decodeResource(resources, Creature.Companion.Species.ORCA.bmpId)
    private val penguinBmp = BitmapFactory.decodeResource(resources, Creature.Companion.Species.PENGUIN.bmpId)

    private val backgroundPaint = Paint()
    private val linePaint = Paint()
    private val textPaint = Paint()

    private var textSize = 0

    lateinit var creaturesList: List<CreatureStepData>

    var drawWorldFlag = false

    private var drawWorldSubscription: Subscription? = null

    //endregion

    init {
        getHolder().addCallback(this)
        backgroundPaint.color = Color.WHITE
        linePaint.color = Color.BLACK
        textPaint.color = Color.BLACK
        textPaint.textSize = textSize.toFloat()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        screenWidth = w
        screenHeight = h
        squareWidth = (screenWidth / World.FIELD_SIZE_X).toFloat()
        textSize = screenHeight / TEXT_SIZE_DIVISOR
        squareHeight = ((screenHeight - textSize) / World.FIELD_SIZE_Y).toFloat()
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        Log.d(TAG, "surfaceCreated")
        drawWorld(p0!!, creaturesList)
    }

    fun drawWorld(holder: SurfaceHolder, creaturesList: List<CreatureStepData>) {
        var canvas: Canvas? = null

        drawWorldSubscription = Completable.fromAction(Action0 {
            drawWorldFlag = true
            while (drawWorldFlag) {
                try {
                    canvas = holder.lockCanvas(null)

                    synchronized(holder) {
                        drawLines(canvas!!)
                        drawCreatures(canvas!!)
                    }
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        })
                .subscribeOn(Schedulers.newThread())
                .subscribe()
    }

    private fun drawLines(canvas: Canvas) {
        canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), backgroundPaint)

        //draw lines
        for (i in 0..fieldSizeX) {
            canvas.drawLine(i * squareWidth, 0f, i * squareWidth, (screenHeight - textSize).toFloat(), linePaint)
        }
        for (i in 0..fieldSizeY) {
            canvas.drawLine(0f, i * squareHeight, screenWidth.toFloat(), i * squareHeight, linePaint)
        }
    }

    private fun drawCreatures(canvas: Canvas) {
        for (creature in creaturesList) {
            drawCreature(canvas, creature)
        }
    }

    fun drawCreature(canvas: Canvas, creatureStepData: CreatureStepData) {
        var scaleFactor = 1f
        if (creatureStepData.age < BMP_SCALE) {
            scaleFactor = (1f + creatureStepData.age % BMP_SCALE) / BMP_SCALE
        }

        val bmpWidth = (scaleFactor * squareWidth).toInt()
        val bmpHeight = (scaleFactor * squareHeight).toInt()

        val paint = Paint()


        if (creatureStepData.isStarvingDeathSoon || creatureStepData.isChildbirthSoon) {

            if (creatureStepData.isStarvingDeathSoon) {
                //soon starving death indication
                paint.color = Color.RED
            } else {
                //soon childbirth indication
                paint.color = Color.GREEN
            }

            canvas.drawRect(squareWidth * creatureStepData.pos.first + (0.5 * (squareWidth - bmpWidth)).toInt(),
                    squareHeight * creatureStepData.pos.second + (0.5 * (squareHeight - bmpHeight)).toInt(),
                    squareWidth * creatureStepData.pos.first + (0.5 * (squareWidth + bmpWidth)).toInt(),
                    squareHeight * creatureStepData.pos.second + (0.5 * squareHeight).toInt(),
                    paint)
        }

        //draw creature
        val bmp = when (creatureStepData.species) {
            Creature.Companion.Species.ORCA -> orcaBmp
            Creature.Companion.Species.PENGUIN -> penguinBmp
        }
        canvas.drawBitmap(Bitmap.createScaledBitmap(bmp, bmpWidth, bmpHeight, false),
                squareWidth * creatureStepData.pos.first + (0.5 * (squareWidth - bmpWidth)).toInt(),
                squareHeight * creatureStepData.pos.second + (0.5 * (squareHeight - bmpHeight)).toInt(), null)

    }

    private fun drawStatistic(canvas: Canvas) {
        //TODO: print statistic
        //canvas.drawText("O: " + orcasQuantity, 0f, screenHeight.toFloat(), textPaint)
        //canvas.drawText("P: " + penguinsQuantity, (screenWidth / 2).toFloat(), screenHeight.toFloat(), textPaint)
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        Log.d(TAG, "surfaceChanged")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        Log.d(TAG, "surfaceDestroyed")
        stopGame()
    }

    fun stopGame() {
        drawWorldFlag = false
        if (drawWorldSubscription != null) {
            drawWorldSubscription!!.unsubscribe()
        }
    }

    companion object {
        private val TAG = "PlayingWorldView"
        private val TEXT_SIZE_DIVISOR = 40
        private val BMP_SCALE = 3
    }
}