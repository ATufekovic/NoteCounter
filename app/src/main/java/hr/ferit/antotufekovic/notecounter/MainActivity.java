package hr.ferit.antotufekovic.notecounter;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NameClickInterface {

    private ImageView iv_create;

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private EntryViewModel mEntryViewModel;

    private String name;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        adapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initializeUI();
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
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View textEntryView = factory.inflate(R.layout.text_entry,null);
                final EditText et_input_name = (EditText) textEntryView.findViewById(R.id.et_text_entry_name);
                final EditText et_input_count = (EditText) textEntryView.findViewById(R.id.et_text_entry_count);
                et_input_name.setText("", TextView.BufferType.EDITABLE);
                et_input_count.setText("", TextView.BufferType.EDITABLE);
                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);


                alert.setView(textEntryView);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean count_flag=false;
                        if(!isEmpty(et_input_name))
                        {
                            name = String.valueOf(et_input_name.getText().toString().trim());
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Empty name field!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(!isEmpty(et_input_count))
                        {
                            count = Integer.parseInt(et_input_count.getText().toString());
                        }
                        else{
                            count_flag = true;
                            count = 0;
                        }

                        Entry entry = new Entry(name,count);
                        if(!mEntryViewModel.listContainsEntryByName(entry)){
                            mEntryViewModel.insert(entry);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Entry already exists!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(count_flag)//moved over here in case the "already exists" triggers
                            Toast.makeText(MainActivity.this, "Empty count field, default to 0", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();

            }
        });
    }

    private void initializeUI() {
        iv_create =findViewById(R.id.iv_create);
    }

    @Override
    public void onDeleteClicked(int position) {
        mEntryViewModel.delete(adapter.getEntries().get(position));
    }

    @Override
    public void onIncrementClicked(int position) {
        mEntryViewModel.incrementUpdate(adapter.getEntries().get(position));
    }

    @Override
    public void onDecrementClicked(int position) {
        mEntryViewModel.decrementUpdate(adapter.getEntries().get(position));
    }

    private boolean isEmpty(EditText etText) {//cant implement this elsewhere due to lack of view
        if (etText.getText().toString().trim().length() > 0)
            return false;
        else
        {
            return true;
        }
    }
}
