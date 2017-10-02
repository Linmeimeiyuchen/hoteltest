package com.example.f1285.hoteltest;

public interface ItemSwipeListener {

    boolean onItemMove(int fromPosition, int toPosition);
    void onItemSwipe(int position);
    void onItemRemove(int position);
}
