package com.example.f1285.hoteltest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

enum ButtonsState {
    GONE,
    LEFT_VISIBLE,
    RIGHT_VISIBLE
}

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback{

    private final static String TAG = "ItemTouchHelperCallback";
    private ItemSwipeListener itemSwipeListener;
    private boolean swipeBack = false;
    private ButtonsState buttonShowedState = ButtonsState.GONE;
    private int buttonWidth;
    private Rect buttonInstance = null;
    private RecyclerView recyclerView;
    private RecyclerView.ViewHolder currentItemViewHolder = null;
    private SwipeButtonAction swipeButtonAction = null;
    private Context context = null;
    private ImageView imageView_delete;

    /*
    public ItemTouchHelperCallback(ItemSwipeListener itemSwipeListener) {
        this.itemSwipeListener = itemSwipeListener;
    }*/

    public ItemTouchHelperCallback(SwipeButtonAction swipeButtonAction, Context context) {
        this.swipeButtonAction = swipeButtonAction;
        this.context = context;
    }

    // 設定 RecyclerView Item 可移動方向
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(0, swipeFlags);
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.d(TAG, "On move!!");
        //itemSwipeListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        //return true;
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d(TAG, "On swipe!!");
        //itemSwipeListener.onItemSwipe(viewHolder.getAdapterPosition());
    }

    // 移動結束之後都會呼叫此
    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        Log.d(TAG, "convertToAbsoluteDirection enter!!");
        // swipeBack 表示移動結束，兩種可能 MotionEvent.ACTION_CANCEL(手移開目標物)，MotionEvent.ACTION_UP(手移開螢幕)
        if (swipeBack) {
            swipeBack = (buttonShowedState != ButtonsState.GONE);
            return 0;   // 以免移動過頭 recyclerview 會將 card 消除
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    // 當 ViewHolder 被移動時會呼叫 onChildDraw
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        Log.d(TAG, "on childDraw!!");
        this.recyclerView = recyclerView;
        // actionState 表示目前 viewHolder 移動方式
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {    // 如果 viewHolder 移動方式為滑動(swipe)時
            if(buttonShowedState != ButtonsState.GONE){     // 當 viewHolder 左滑 buttonShowedState = ButtonState.RIGHT_VISIBLE，因為要顯示右邊的 button，反之
                //if (buttonShowedState == ButtonsState.LEFT_VISIBLE) dX = Math.max(dX, buttonWidth);
                if (buttonShowedState == ButtonsState.RIGHT_VISIBLE)
                    dX = Math.min(dX, -buttonWidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        if (buttonShowedState == ButtonsState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        currentItemViewHolder = viewHolder;
    }

    private void setTouchListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch!!");
                swipeBack = (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP);
                if (swipeBack) {
                    Log.d(TAG, "dX = " +dX);
                    imageView_delete = (ImageView) viewHolder.itemView.findViewById(R.id.delete_button);
                    imageView_delete.setVisibility(View.GONE);
                    if (dX < -buttonWidth){
                        buttonShowedState = ButtonsState.RIGHT_VISIBLE;
                        //Log.d(TAG, "Swipe Left!!");
                    }
                    else if (dX > buttonWidth){
                        buttonShowedState  = ButtonsState.LEFT_VISIBLE;
                        //Log.d(TAG, "Swipe Right!!");
                    }

                    if (buttonShowedState != ButtonsState.GONE) {
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });
    }

    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.d(TAG, "touchDown Enter!!");
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.d(TAG, "touchUp Enter!!");
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ItemTouchHelperCallback.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    setItemsClickable(recyclerView, true);
                    swipeBack = false;

                    if (swipeButtonAction != null && buttonInstance != null && buttonInstance.contains((int) event.getX(), (int) event.getY()))
                        swipeButtonAction.onRightClicked(viewHolder.getAdapterPosition());

                    buttonShowedState = ButtonsState.GONE;
                    currentItemViewHolder = null;
                }
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder) {

        View itemView = viewHolder.itemView;
        buttonWidth = (int) itemView.getResources().getDimension(R.dimen.button_cardview_delete);
        Paint p = new Paint();

        /*
        RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + buttonWidthWithoutPadding, itemView.getBottom());
        p.setColor(Color.BLUE);
        c.drawRoundRect(leftButton, corners, corners, p);
        drawText("EDIT", c, leftButton, p);*/

        //RectF rightButton = new RectF(itemView.getRight() - buttonWidth, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        Rect rightButton = new Rect(itemView.getRight() - buttonWidth, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        Rect background = new Rect(itemView.getLeft(), itemView.getTop(), itemView.getRight() - buttonWidth, itemView.getBottom());
        p.setColor(ContextCompat.getColor(context, R.color.recyclerView_delete_button));
        c.drawRect(rightButton, p);
        c.drawRect(background, p);
        drawImage(viewHolder, c, rightButton, p);

        buttonInstance = null;
        if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
            //buttonInstance = leftButton;
        }
        else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
            buttonInstance = rightButton;
        }
    }

    private void drawImage(RecyclerView.ViewHolder viewHolder, Canvas c, Rect button, Paint p){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.guest_ic_can);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, dpToPx(40), dpToPx(40), true);
        p.setColor(ContextCompat.getColor(context, R.color.textView_cardView_booking));
        c.drawBitmap(resizedBitmap, button.centerX()-(resizedBitmap.getWidth()/2), button.centerY()-(resizedBitmap.getHeight()/2), p);
        bitmap.recycle();
    }

    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 60;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX()-(textWidth/2), button.centerY()+(textSize/2), p);
    }

    public void onDraw(Canvas c) {
        if (currentItemViewHolder != null) {
            drawButtons(c, currentItemViewHolder);
        }
    }

    public int dpToPx(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

}
