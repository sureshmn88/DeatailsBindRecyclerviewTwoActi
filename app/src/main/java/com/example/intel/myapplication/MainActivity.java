package com.example.intel.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView alertTxt;

    RecyclerView mRecyclerView;

    FloatingActionButton addfloatingbtn;
    ArrayList<Product> mList=new ArrayList<>();

    Product item;

    ProductData mAdapter;


    int Requestcode=20;
    int requestCodeForResult=21;
    int selectPosition=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intilize();

        addfloatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddDataList.class);
                startActivityForResult(intent,Requestcode);
            }
        });
    }

    void intilize() {

        alertTxt=(TextView)findViewById(R.id.data_txt);
        mRecyclerView=(RecyclerView)findViewById(R.id.recyler_list);
        addfloatingbtn=(FloatingActionButton)findViewById(R.id.float_btn);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){

            if(requestCode==Requestcode){
                //Intent intent=getIntent();

                if(data.hasExtra("sentdata")){
                    item=data.getParcelableExtra("sentdata");
                    mList.add(item);
                    fetchDataToRecycelerView();
                }
            }else if(requestCode==requestCodeForResult){
                Product itemupdated;
                if(data.hasExtra("updatedata")){


                    itemupdated=data.getParcelableExtra("updatedata");
                    mList.set(selectPosition,itemupdated);
                    fetchDataToRecycelerView();
                }
            }
        }


    }

    void fetchDataToRecycelerView() {


            if(mList.size()>0) {
                mRecyclerView.setVisibility(View.VISIBLE);
                alertTxt.setVisibility(View.GONE);

                mAdapter = new ProductData(MainActivity.this, mList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                mAdapter.setOnClickListener(new ProductData.OnClickListener() {
                    @Override
                    public void onLayoutClick(int position) {
                        selectPosition = position;

                        Intent intent = new Intent(MainActivity.this, AddDataList.class);
                        intent.putExtra("getdata", mList.get(selectPosition));
                        startActivityForResult(intent, requestCodeForResult);
                    }
                });
            }else {

                mRecyclerView.setVisibility(View.GONE);
                alertTxt.setVisibility(View.VISIBLE);
            }


    }
}
