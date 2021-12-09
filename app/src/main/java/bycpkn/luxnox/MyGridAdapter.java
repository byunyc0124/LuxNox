package bycpkn.luxnox;

import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MyGridAdapter extends BaseAdapter {
    Context context;
    View dialogView;
    ImageView ivPoster;
    Integer[] posterID = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};
    Integer[] posterTitle = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};

    public MyGridAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return posterID.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(400,650));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(5, 5, 10, 10);

        imageView.setImageResource(posterID[position]);

        final int pos = position;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView = (View) View.inflate(context, R.layout.dialog, null);
                AlertDialog.Builder dig = new AlertDialog.Builder(context);
                ivPoster = (ImageView) dialogView.findViewById(R.id.imageViewForPoster);
                ivPoster.setImageResource(posterID[pos]);
                dig.setTitle(posterTitle[pos]);
                dig.setIcon(posterID[pos]);
                dig.setView(dialogView);
                dig.setNegativeButton("닫기", null);
                dig.show();
            }
        });
        return imageView;
    }
}

