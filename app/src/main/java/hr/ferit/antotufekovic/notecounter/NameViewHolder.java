package hr.ferit.antotufekovic.notecounter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tv_Cell_name;
    private TextView tv_Cell_count;
    private ImageView iv_Cell_increment;
    private ImageView iv_Cell_decrement;
    private ImageView iv_Cell_delete;
    private NameClickInterface clickInterface;


    public NameViewHolder(View itemView, NameClickInterface listener) {
        super(itemView);

        this.clickInterface = listener;

        tv_Cell_name = itemView.findViewById(R.id.tv_cell_name);
        tv_Cell_count = itemView.findViewById(R.id.tv_cell_count);
        iv_Cell_increment = itemView.findViewById(R.id.iv_cell_increment);
        iv_Cell_decrement = itemView.findViewById(R.id.iv_cell_decrement);
        iv_Cell_delete = itemView.findViewById(R.id.iv_cell_delete);

        iv_Cell_delete.setOnClickListener(this);
        iv_Cell_increment.setOnClickListener(this);
        iv_Cell_decrement.setOnClickListener(this);
    }

    public void setName(String name) {
        tv_Cell_name.setText(name);
    }

    public void setCount(int count) {
        tv_Cell_count.setText(String.valueOf(count));
    }


    @Override
    public void onClick(View v) {

        String name = String.valueOf(tv_Cell_name.getText());
        int count = Integer.parseInt(tv_Cell_count.getText().toString());
        Entry unit = new Entry(name,count);

        if (v == iv_Cell_delete)
            clickInterface.onDeleteClicked(getAdapterPosition());
        else if (v == iv_Cell_increment) {
            clickInterface.onIncrementClicked(getAdapterPosition());
        }
        else if (v == iv_Cell_decrement) {
            clickInterface.onDecrementClicked(getAdapterPosition());
        }
    }
}
