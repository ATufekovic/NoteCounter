package hr.ferit.antotufekovic.notecounter;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NameClickInterface {

    private EditText et_item_name;
    private EditText et_item_count;
    private ImageView iv_create;

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private EntryViewModel mEntryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView=findViewById(R.id.recyclerView);
        adapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initializeUI();
        //setupRecyclerData();
        setUpListeners();

        mEntryViewModel=ViewModelProviders.of(this).get(EntryViewModel.class);
        mEntryViewModel.getAllEntries().observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable List<Entry> entries) {
                adapter.setEntries(entries);
            }
        });
    }

    private void setUpListeners() {
        iv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                int count;

                if(!isEmpty(et_item_name))
                {
                    name = String.valueOf(et_item_name.getText());
                }
                else
                    return;

                if(!isEmpty(et_item_count))
                {
                    count = Integer.parseInt(et_item_count.getText().toString());
                }
                else
                    return;

                Entry entry = new Entry(name,count);
                //adapter.insertNewItem(entry,adapter.getItemCount());
                //not using adapters innate methods directly anymore
                mEntryViewModel.insert(entry);

                et_item_name.setText("");
                et_item_count.setText("");
            }
        });
    }

    private void setupRecyclerData() {
        List<Entry> data = new ArrayList<>();


        //TODO: sharedPreference loading on startup
        data.add(new Entry("km",0));


        adapter.addEntries(data);
    }

    private void initializeUI() {
        et_item_name=findViewById(R.id.et_item_name);
        et_item_count=findViewById(R.id.et_item_count);
        iv_create =findViewById(R.id.iv_create);
    }

    @Override
    public void onDeleteClicked(int position) {
        //adapter.removeEntry(position);
        mEntryViewModel.delete(adapter.getEntries().get(position));
    }

    @Override
    public void onIncrementClicked(int position, Entry entry) {
        /*String name = entry.getName();
        int count = entry.getCount();
        count++;
        Entry updatedUnit = new Entry(name,count);

        adapter.changeEntry(updatedUnit,position);
        adapter.notifyItemChanged(position);*/
        //done did, my previous methods were pretty stupid
        mEntryViewModel.incrementUpdate(entry);
    }

    @Override
    public void onDecrementClicked(int position, Entry entry) {
        /*String name = entry.getName();
        int count = entry.getCount();
        count--;
        Entry updatedUnit = new Entry(name,count);

        adapter.changeEntry(updatedUnit,position);
        adapter.notifyItemChanged(position);*/
        //done did
        mEntryViewModel.decrementUpdate(entry);
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;
        else
        {
            Toast.makeText(this, "Empty input field(s)", Toast.LENGTH_SHORT).show();
            return true;//should probably implement this somewhere else hmm
        }
    }
}
