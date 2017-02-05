package com.alfa.news.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alfa.news.R;
import com.alfa.news.model.record.ItemRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsAdapter extends ArrayAdapter<ItemRecord> {

    public ItemsAdapter(Context context, List<ItemRecord> records) {
        super(context, R.layout.list_item, records);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item, parent, false);
            view.setTag(new ViewHolder(view));
        } else {
            view = convertView;
        }
        final ItemRecord record = getItem(position);
        if (record != null) {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.title.setText(record.getTitle());
            holder.description.setText(Html.fromHtml(record.getDescription()));
            holder.date.setText(record.getDate());
        }
        return view;
    }

    protected class ViewHolder {

        @BindView(R.id.item_title)
        protected TextView title;
        @BindView(R.id.item_description)
        protected TextView description;
        @BindView(R.id.item_date)
        protected TextView date;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}