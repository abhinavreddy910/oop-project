package com.example.myapplication;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewDialogList {

    DataBaseHelper db;
    int Catg_ID;
    EditText input_name,input_quantity;
    List l;
    public ViewDialogList(int Catg_ID,DataBaseHelper db,List l)
    {
        this.l = l;
        this.Catg_ID = Catg_ID;
        this.db = db;
    }
    public void Dialog_Add(Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout_list);

        input_name = (EditText)dialog.findViewById(R.id.input_name);

        Button add = (Button) dialog.findViewById(R.id.edit);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code to store data into database
                String new_name = input_name.getText().toString();
                boolean valid = true ;
                Cursor cursor = db.viewTasks_List(Catg_ID);
                while ( cursor.moveToNext() )
                {
                    String a = cursor.getString(1);
                    if(a.equals(new_name))
                        valid = false;
                }

                if(valid)
                {
                    db.insertTask_List(new_name,Catg_ID);
                    dialog.dismiss();
                    l.viewList();
                }
                else
                    Toast.makeText(activity,"An item with this name already exists",Toast.LENGTH_LONG).show();

            }
        });
        Button dialogButton2 = (Button) dialog.findViewById(R.id.subtract);
        dialogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void Dialog_Edit(Activity activity, String original_name) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout_list);

        input_name = (EditText)dialog.findViewById(R.id.input_name);

        input_name.setText(original_name);

        Button edit = (Button) dialog.findViewById(R.id.edit);
        edit.setText("Edit");
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code to store data into database
                String new_name = input_name.getText().toString();

                boolean valid = true ;
                Cursor cursor = db.viewTasks_List(Catg_ID);
                while ( cursor.moveToNext() )
                {
                    String a = cursor.getString(1);
                    if(a.equals(new_name) && !original_name.equals(new_name))
                        valid = false;
                }

                if(valid)
                {
                    db.editTasks_List(Catg_ID,original_name,new_name);
                    dialog.dismiss();
                    l.viewList();
                }
                else
                    Toast.makeText(activity,"An item with this name already exists",Toast.LENGTH_LONG).show();
            }
        });
        Button dialogButton2 = (Button) dialog.findViewById(R.id.subtract);
        dialogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}