package com.example.prilogulka;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prilogulka.data_base.UserLoginDB;
import com.example.prilogulka.menu.RVAdapter;
import com.example.prilogulka.data.User;

public class ListOfUsersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_list_of_users, container, false);
        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);


        UserLoginDB userDB = new UserLoginDB(getContext());
        userDB.dropTable();
        userDB.createTable();
        userDB.insertUser(new User("vldrshv@gmail.com", "1234", "112233", true));
        userDB.insertUser(new User("eva13u113@gmail.com", "9999", "778899", true));


        RVAdapter adapter = new RVAdapter(userDB.selectAll());
        rv.setAdapter(adapter);
        return rootView;
    }


}

