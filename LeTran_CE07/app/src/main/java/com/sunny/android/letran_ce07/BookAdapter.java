
    // Name: Tran Le
    // JAV1 - 1808
    // File name: BookAdapter.java

package com.sunny.android.letran_ce07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

class BookAdapter extends BaseAdapter {

    // Member variables
    private static final long BASE_ID = 0x00001;
    private Context bContext = null;
    private ArrayList<Book> library = null;

    // Constructor
    BookAdapter(Context bContext, ArrayList<Book> library) {
        this.bContext = bContext;
        this.library = library;
    }

    @Override
    public int getCount() {
        if (library != null) {
            return library.size();
        }

        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (library != null && position >= 0 || position < library.size()) {
            return library.get(position);
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return BASE_ID + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(bContext).inflate(R.layout.gridview_custom, parent,
                    false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder)convertView.getTag();
        }

        Book booklet = (Book)getItem(position);
        if (booklet != null) {
            vh.txt_BookTitle.setText(booklet.getBookTitle());
            vh.ivw_SmartImageView.setImageUrl(booklet.getImageUrl());
        }

        return convertView;
    }

    // Instantiate recycle view
    static class ViewHolder {
        final SmartImageView ivw_SmartImageView;
        final TextView txt_BookTitle;

        ViewHolder(View _layout) {
            ivw_SmartImageView = (SmartImageView)_layout.findViewById(R.id.ivw_SmartImageView);
            txt_BookTitle = (TextView)_layout.findViewById(R.id.txt_BookTitle);
        }
    }
}
