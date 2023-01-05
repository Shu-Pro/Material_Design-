package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.as40_materialtest.FruitActivity;
import com.example.as40_materialtest.R;

import java.util.List;

import pojo.Fruit;


//继承RecyclerView.Adapter需要指定自己的ViewHolder为其泛型
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private Context mContext;
    private List<Fruit> mFruitList;

    public FruitAdapter(List<Fruit> fruitList) {
        mFruitList=fruitList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            fruitImage = (ImageView)itemView.findViewById(R.id.fruit_image);
            fruitName = (TextView) itemView.findViewById(R.id.fruit_name);


        }

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(cardView);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                int position =viewHolder.getLayoutPosition();
                Fruit fruit = mFruitList.get(position);
                Intent intent = new Intent(mContext, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                mContext.startActivity(intent);
            }
        });


        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        //Glide进行图片的像素调整，不造成内存溢出
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);


    }





    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
