package com.example.booksearchapp.adapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.booksearchapp.model.Book;
import com.example.booksearchapp.R;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<com.example.booksearchapp.adapter.RecyclerViewAdapter.viewHolder> {

    private Context mContext;
    private List<Book> mData;
    private RequestOptions options;

    public RecyclerViewAdapter(Context mContext, ArrayList<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;
        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.book_raw_item , parent , false);
        final viewHolder viewHolder =  new viewHolder(view);
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getAdapterPosition();
                StringBuffer str = new StringBuffer(mData.get(pos).getPreview());
                str.insert(4,'s');
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(str)));
                mContext.startActivity(i);
                Log.e("something", String.valueOf(str));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int i) {
        Book book = mData.get(i);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthors());
        holder.tvPublish.setText(book.getPublishedDate());
//        holder.tvPage.setText(book.getPageCount());
        Glide.with(mContext).load(book.getThumbnail()).apply(options).into(holder.ivThumbnail);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        ImageView ivThumbnail ;
        TextView tvTitle , tvAuthor, tvPublish, tvPage;
        LinearLayout container ;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.thumbnail);
            tvTitle = itemView.findViewById(R.id.title);
            tvAuthor = itemView.findViewById(R.id.author);
            tvPublish = itemView.findViewById(R.id.publish);
//            tvPage = itemView.findViewById(R.id.page);
            container = itemView.findViewById(R.id.container);
        }
    }
}
