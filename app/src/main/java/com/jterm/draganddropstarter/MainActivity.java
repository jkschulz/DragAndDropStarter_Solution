package com.jterm.draganddropstarter;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the element to listen to
        final TextView dragBox = (TextView) findViewById(R.id.drag_box);

        // Set listener
        dragBox.setOnTouchListener(
                // Create a listener using an anonymous class
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            view.startDrag(
                                    ClipData.newPlainText("", ""),
                                    new View.DragShadowBuilder(dragBox),
                                    dragBox,
                                    0
                            );

                            return true;
                        }
                        // Return false because we didn't handle it.
                        return false;
                    }
                }
        );

        // Find the containers
        final LinearLayout left = (LinearLayout) findViewById(R.id.left_area);
        final LinearLayout right = (LinearLayout) findViewById(R.id.right_area);

        // Set drag listeners on all containers
        left.setOnDragListener(new DragListener());
        right.setOnDragListener(new DragListener());

    }

    private class DragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            switch(dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // MUST return true to enable dropping
                    return true;
                case DragEvent.ACTION_DROP:
                    View dragBox = findViewById(R.id.drag_box);
                    View parent = (View) dragBox.getParent();
                    if (parent instanceof LinearLayout && view instanceof LinearLayout) {
                        LinearLayout oldParent = (LinearLayout) parent;
                        oldParent.removeView(dragBox);
                        LinearLayout newParent = (LinearLayout) view;
                        newParent.addView(dragBox);
                        return true;
                    }
                    return false;
                default:
                    return false;
            }
        }
    }
}
