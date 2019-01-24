package hr.ferit.antotufekovic.notecounter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<NameViewHolder> {

    private List<Entry> entryList = new ArrayList<>();

    private NameClickInterface clickInterface;

    public RecyclerAdapter(NameClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_name, parent, false);
        return new NameViewHolder(view, clickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder nameViewHolder, int position) {
        if(entryList !=null)
        {
            nameViewHolder.setName(entryList.get(position).getName());
            nameViewHolder.setCount(entryList.get(position).getCount());
        }
        else
        {
            nameViewHolder.setName("oops");
            nameViewHolder.setCount(404);
        }
    }

    @Override
    public int getItemCount() {
        if (entryList !=null)
            return entryList.size();
        else
            return 0;
    }

    public void addEntries(List<Entry> entries) {//this one just adds if you call it
        this.entryList.addAll(entries);
        notifyDataSetChanged();
    }

    public void setEntries(List<Entry> entries)//this one wipes and then adds entries
    {
        this.entryList.clear();
        addEntries(entries);
    }

    public List<Entry> getEntries()
    {
        return entryList;
    }

    /*public void insertNewItem(String name,int count, int position) {
        if (position >= 0 && position <= entryList.size()) {
            entryList.add(position, new Entry(name,count));
            notifyItemInserted(position);
        }
    }
    public void insertNewItem(Entry unit, int position)
    {
        if (position >= 0 && position <= entryList.size()) {
            entryList.add(position, unit);
            notifyItemInserted(position);
        }
    }
    public void changeEntry(Entry entry, int position)
    {
        if(entryList.size()<position+1)
            return;
        else
        {
            entryList.set(position,entry);
        }
    }

    public void removeEntry(int position) {
        if (position >= 0 && position < entryList.size()) {
            entryList.remove(position);
            notifyItemRemoved(position);
        }
    }*/
}
