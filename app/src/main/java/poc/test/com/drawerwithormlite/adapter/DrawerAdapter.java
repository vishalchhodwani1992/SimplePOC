package poc.test.com.drawerwithormlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import poc.test.com.drawerwithormlite.R;

/**
 * Created by ashishthakur on 21/2/18.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.Holder> {


    ArrayList<String> list;
    Context context;
    OnItemClick onItemClick;


    public DrawerAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }


    public void setOnContactClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_adapter, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {

        holder.userName.setText(list.get(position));
        holder.contactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(v.getId(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClick {
        public void onClick(int id, int pos);
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView userName, userNumber;
        LinearLayout contactLayout;

        public Holder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userNumber = (TextView) itemView.findViewById(R.id.userNumber);
            contactLayout = (LinearLayout) itemView.findViewById(R.id.contactLayout);

        }
    }

}
