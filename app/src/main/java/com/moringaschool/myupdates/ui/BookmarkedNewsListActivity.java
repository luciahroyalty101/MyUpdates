package com.moringaschool.myupdates.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.myupdates.R;
import com.moringaschool.myupdates.adapters.articleAdapter;
import com.moringaschool.myupdates.models.Article;
import com.moringaschool.myupdates.util.Constants;

public class BookmarkedNewsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    articleAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();


        mbase = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_TOP_HEADLINES)
                .child(uid);

        recyclerView = findViewById(R.id.recycler1);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Article> options = new FirebaseRecyclerOptions.Builder<Article>()
                .setQuery(mbase, Article.class)
                .build();

        adapter = new articleAdapter(options);
        recyclerView.setAdapter(adapter);
    }


    @Override protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

