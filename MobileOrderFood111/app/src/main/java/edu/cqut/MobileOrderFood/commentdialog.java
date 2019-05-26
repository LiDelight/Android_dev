package edu.cqut.MobileOrderFood;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class commentdialog extends Dialog
{
    //保存和传递用户输入的用户名和密码
    public String mUserName = null;
    //public String mPaswrd = null;
    public commentdialog(Context context) {
        super(context);
        //设置返回键不能返回
        setCancelable(false);
        setContentView(R.layout.commentlist);
        final EditText etName = (EditText)findViewById(R.id.etUserName);
        //	final EditText edPaswrd = (EditText)findViewById(R.id.etPaswrd);
        Button button1=(Button)findViewById(R.id.btnOK);
        Button button2=(Button)findViewById(R.id.btnCancel);

        Button.OnClickListener buttonListener=new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.btnOK:
                        mUserName = etName.getText().toString();//评论信息
                        //	mPaswrd = edPaswrd.getText().toString();
                        break;
                    case R.id.btnCancel:
                        break;
                }
                dismiss(); //对话框销毁
            }
        };
        button1.setOnClickListener(buttonListener);
        button2.setOnClickListener(buttonListener);
    }
}
