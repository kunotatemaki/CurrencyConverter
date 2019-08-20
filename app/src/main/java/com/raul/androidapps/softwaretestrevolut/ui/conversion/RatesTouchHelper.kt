package com.raul.androidapps.softwaretestrevolut.ui.conversion

import android.os.Build
import android.os.VibrationEffect
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.softwaretestrevolut.R
import timber.log.Timber

class RatesTouchHelper(callback: Callback) : ItemTouchHelper(callback) {


    class Callback constructor() : ItemTouchHelper.SimpleCallback(UP, 0) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            (recyclerView.adapter as? ConversionAdapter)?.onItemMoved(
                fromPosition = viewHolder.adapterPosition,
                toPosition = target.adapterPosition
            )
            return true
        }

        override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {}

        override fun onSelectedChanged(
            viewHolder: RecyclerView.ViewHolder?,
            actionState: Int
        ) {
            super.onSelectedChanged(viewHolder, actionState)

            if (actionState == ACTION_STATE_DRAG) {
//                viewHolder?.itemView?.alpha = 0.5f
                Timber.d("rukia pulsado")
                viewHolder?.itemView?.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.colorPrimary))
            }
        }

        override fun clearView(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) {
            super.clearView(recyclerView, viewHolder)
//            viewHolder.itemView.alpha = 1.0f
            (recyclerView.adapter as? ConversionAdapter)?.resetBasePrice()

            viewHolder?.itemView?.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.context, android.R.color.white))
//            if (viewHolder is BetHistoryBookmakerViewHolder) {
//                viewHolder.getBookieCodeBound()?.let { BookieCode ->
//                    val event = InteractionEvent("Bookie_Reorder", BookieCode)
//                    Analytics.trackEvent(event)
//                }
            }


        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return false
        }

    }

}