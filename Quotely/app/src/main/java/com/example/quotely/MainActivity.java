package com.example.quotely;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quotely.Database.Database;
import com.example.quotely.Model.ModelClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

   Random random= new Random();
    private Database myDB;
    private List<ModelClass> mylist;
    private int currentIndex = 0;
    String randomQuote="";
    String quoteAuthor="";
    TextView author,quote;
    FloatingActionButton refreshBtn,favoriteBtn;
    ImageView options;
    final boolean[] isFilled = {false};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new Database(MainActivity.this);
        mylist= new ArrayList<>();
        author=findViewById(R.id.quoteAuthor);
          quote=findViewById(R.id.quoteText);
         refreshBtn=findViewById(R.id.RefreshBtn);
       // FloatingActionButton   shareBtn=findViewById(R.id.ShareBtn);
         favoriteBtn=findViewById(R.id.FavBtn);
         options= findViewById(R.id.optionBtn);
        mylist= myDB.getData();
        Intent i = getIntent();
        List<ModelClass> updatedList = (List<ModelClass>) i.getSerializableExtra("List");

        if (updatedList != null) {
            mylist.clear();
            mylist.addAll( updatedList);
            //Toast.makeText(this,"list updated", Toast.LENGTH_SHORT).show();
            // Update UI with the updated list
        }
        final ModelClass[] data ;
        if (!mylist.isEmpty()) {
            ModelClass[] tempArray = {mylist.get(currentIndex)};
            data = tempArray;
        }else {
            data = new ModelClass[0]; // Empty array for data if mylist is empty
        }



//        int rowCount = myDB.getRowCount(); // Assuming myDB is an instance of your DatabaseHelper
//        Log.d("Row Count", "Number of rows: " + rowCount);

     //   Toast.makeText(this,String.valueOf(mylist.size()), Toast.LENGTH_SHORT).show();


        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                display(quote,author);
//                if (!mylist.isEmpty()){
//                    currentIndex = (currentIndex + 1) % mylist.size();
//                    data[0] =mylist.get(currentIndex);
//
//
//                }
                checkQuote(randomQuote);

            }
        });
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFilled[0]) {
                    favoriteBtn.setImageResource(R.drawable.fav_ic); // Change to fav_ic
                    isFilled[0] = false;
                    myDB.deleteQuoteByQuote(randomQuote);
                } else {
                    favoriteBtn.setImageResource(R.drawable.fav_fill_ic); // Change to fav_fill_ic
                    isFilled[0] = true;
                    myDB.insertData(randomQuote,quoteAuthor);

                }
                mylist= myDB.getData();


            }
        });
//        shareBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                share(quote,author);
//            }
//        });

        options.setOnClickListener(view -> showPopUp(view,quote,author));

        display(quote,author);
        if (!mylist.isEmpty()){
            checkQuote(randomQuote);
        }
    }
public void checkQuote(String specificQuote){


        for (ModelClass data : mylist) {
            String quoteFromDatabase = data.getQuote();

            if (quoteFromDatabase.equals(specificQuote)) {

                favoriteBtn.setImageResource(R.drawable.fav_fill_ic); // Change to fav_fill_i
                isFilled[0] = true;
                //  Toast.makeText(this, "Quote matched!", Toast.LENGTH_SHORT).show();
                break; // Break the loop since we found a match
            }
            else {
                favoriteBtn.setImageResource(R.drawable.fav_ic); // Change to fav_ic
                isFilled[0] = false;
            }
        }



}
    private void showPopUp(View v, TextView quote,TextView author) {
        PopupMenu popupMenu= new PopupMenu(MainActivity.this,v);
        popupMenu.inflate(R.menu.option_menu);
        popupMenu.setOnMenuItemClickListener(item ->{
            switch (item.getItemId()){
                case R.id.menuFavorite:
                    Intent intent = new Intent(MainActivity.this, Favorite.class);
                    startActivity(intent);
                    //Toast.makeText(context,"Delete", Toast.LENGTH_SHORT).show();
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

    public  void display(TextView quote,TextView author){
        int randomNum = new Random().nextInt(10) + 1;


        switch (randomNum){
            case 1:
                randomQuote=getResources().getString(R.string.quote1);
                quoteAuthor=getString(R.string.aurthor1);
                break;
            case 2:
                randomQuote=getResources().getString(R.string.quote2);
                quoteAuthor=getString(R.string.aurthor2);
                break;
            case 3:
                randomQuote=getResources().getString(R.string.quote3);
                quoteAuthor=getString(R.string.aurthor3);
                break;
            case 4:
                randomQuote=getResources().getString(R.string.quote4);
                quoteAuthor=getString(R.string.aurthor4);
                break;
            case 5:
                randomQuote=getResources().getString(R.string.quote5);
                quoteAuthor=getString(R.string.aurthor5);
                break;
            case 6:
                randomQuote=getResources().getString(R.string.quote6);
                quoteAuthor=getString(R.string.aurthor6);
                break;
            case 7:
                randomQuote=getResources().getString(R.string.quote7);
                quoteAuthor=getString(R.string.aurthor7);
                break;
            case 8:
                randomQuote=getResources().getString(R.string.quote8);
                quoteAuthor=getString(R.string.aurthor8);
                break;
            case 9:
                randomQuote=getResources().getString(R.string.quote9);
                quoteAuthor=getString(R.string.aurthor9);
                break;
            case 10:
                randomQuote=getResources().getString(R.string.quote10);
                quoteAuthor=getString(R.string.aurthor10);
                break;

        }
       // String msg="Random Number: " + randomNum;
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

            quote.setText(randomQuote);
            author.setText(quoteAuthor);
    }
    public  void share(TextView quote,TextView author) {
        String shareContent = "\"" + quote.getText() + "\"" +"\n\n " + author.getText();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        startActivity(shareIntent);
    }
}