package com.example.quotely;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quotely.Database.Database;
import com.example.quotely.Model.ModelClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Favorite extends AppCompatActivity {

    TextView quote_view,author_view;
    FloatingActionButton refreshBtn,favoriteBtn;
    ImageView options;
    private Database myDB;
    private List<ModelClass> mylist;
    private int currentIndex = 0;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("List",(Serializable) mylist);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite);

        myDB = new Database(Favorite.this);
        mylist= new ArrayList<>();
        quote_view=findViewById(R.id.fav_quoteText);
        author_view=findViewById(R.id.fav_quoteAuthor);
        refreshBtn=findViewById(R.id.fav_RefreshBtn);
        favoriteBtn=findViewById(R.id.fav_FavBtn);
        options=findViewById(R.id.fav_optionBtn);

        mylist= myDB.getData();

        if(mylist.isEmpty())
        {
            quote_view.setText("No Favorite Quotes");
            author_view.setText("");
            favoriteBtn.setVisibility(View.GONE);
            refreshBtn.setVisibility(View.GONE);
            options.setVisibility(View.GONE);
            quote_view.setGravity(Gravity.CENTER);
            quote_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
        else {
            final ModelClass[] data = {mylist.get(currentIndex)};
            final String[] author = {data[0].getAuthor()};
            final String[] quote = {data[0].getQuote()};
            if (data[0] != null) {

                display(quote[0], author[0]);

            }
            options.setOnClickListener(view -> showPopUp(view,quote,author));
            favoriteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = data[0].getId();
                    myDB.deleteQuoteById(id);
                   // Toast.makeText(Favorite.this,"Quote Removed", Toast.LENGTH_SHORT).show();
                    mylist= myDB.getData();
                    if(mylist.isEmpty())
                    {
                        quote_view.setText("No Favorite Quotes");
                        author_view.setText("");
                        favoriteBtn.setVisibility(View.GONE);
                        refreshBtn.setVisibility(View.GONE);
                        options.setVisibility(View.GONE);
                        quote_view.setGravity(Gravity.CENTER);
                        quote_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    }
                    else {

                        currentIndex = (currentIndex + 1) % mylist.size();
                        data[0] =mylist.get(currentIndex);
                        author[0] = data[0].getAuthor();
                        quote[0] = data[0].getQuote();
                        if (data[0] != null) {

                            display(quote[0], author[0]);
                        }
                    }

                }
            });
            refreshBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentIndex = (currentIndex + 1) % mylist.size();
                    data[0] =mylist.get(currentIndex);
                    author[0] = data[0].getAuthor();
                    quote[0] = data[0].getQuote();
                    if (data[0] != null) {

                        display(quote[0], author[0]);
                    }


                }
            });
        }


        // Other initialization code if needed
    }
    private void showPopUp(View v, String[] quote, String[] author) {
        PopupMenu popupMenu= new PopupMenu(Favorite.this,v);
        popupMenu.inflate(R.menu.option_menu);
        popupMenu.setOnMenuItemClickListener(item ->{
            switch (item.getItemId()){
                case R.id.menuFavorite:

                    return true;
                case R.id.menuShare:
                    share(quote,author);
                    //Toast.makeText(context,"Update", Toast.LENGTH_SHORT).show();

                    break;

            }
            return false;
        });
        popupMenu.show();
    }
    public  void share(String[] quote, String[] author) {
        String shareContent = "\"" + quote[0] + "\"" +"\n\n " + author[0];
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        startActivity(shareIntent);
    }


    public  void display(String quote_text,String quote_author){
        //int randomNum = new Random().nextInt(10) + 1;

        // String msg="Random Number: " + randomNum;
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        quote_view.setText(quote_text);
        author_view.setText(quote_author);
    }
}
