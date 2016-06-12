package android.hmkcode.com.myapplication123.UsersList;

import android.app.Activity;
import android.hmkcode.com.myapplication123.Classes.User;
import android.hmkcode.com.myapplication123.R;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {

    private List<User> users;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        users = new ArrayList<>();
        users.add(new User("Ahmed Hamdy", "ahmedhamdy2222@gmail.com"));

    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(users);
        rv.setAdapter(adapter);
    }
}
