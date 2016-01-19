package mx.evin.apps.words.view.decorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Suleiman on 26-07-2015.
 */
public class HorizontalSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int mSpace;

    public HorizontalSpacesItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = mSpace;
        outRect.bottom = mSpace;
        outRect.top = mSpace;

        if (parent.getChildAdapterPosition(view) < 1)
            outRect.left = mSpace;
    }
}