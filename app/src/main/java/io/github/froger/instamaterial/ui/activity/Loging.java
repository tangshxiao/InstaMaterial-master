package io.github.froger.instamaterial.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.navi.demo.main.Person;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import io.github.froger.instamaterial.R;

public class Loging extends BaseActivity {

    private AutoCompleteTextView email;
    private EditText password;
    private View login_progress;
    private View login_form;
    private Button email_sign_in_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loging);
        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(email.getText().toString(),password.getText().toString());
//                addSqlData();
//                registerData();
            }
        });
    }



    /**
     * 添加一条数据
     */
    public void   addSqlData(){
        Person person=new Person();
        person.setName("开始");
        person.setAddress("结束");
        person.save(new SaveListener<String> (){
            @Override
            public void done(String s, BmobException e) {
                    if(e==null){
                        toast("添加数据成功，返回objectId为：" + e);
                    }else {
                        toast("创建数据失败：" + e.getMessage());
                    }
            }
        } );
    }

    private void toast(String context){
        Toast.makeText(this,context,Toast
        .LENGTH_SHORT).show();
    }

    /**
     * 查询一条数据
     */

    private void querySqlData(){
        BmobQuery  bombquery= new BmobQuery<Person>();
        bombquery.getObject("90fe4d29e7", new QueryListener<Person> (){
            @Override
            public void done(Person person, BmobException e) {
                if(e==null){
                    toast("查询成功"+person.getName());
                }else{
                    toast("查询失败：" + e.getMessage());
                }
            }
        });
    }

    /**
     * 修改一行数据
     */

    private void updateSqlData(){
       final Person person=new Person();
        person.setName("唐大哥");
        person.update("90fe4d29e7",new UpdateListener(){
            @Override
            public void done(BmobException e) {
                if(e==null){
                    toast("更新成功:"+person.getUpdatedAt());
                }else{
                    toast("更新失败：" + e.getMessage());
                }
            }



        });
    }

    /**
     * 删除一行数据
     */

    private void deleteSqlData(){
      final   Person person=new Person();
        person.setObjectId("90fe4d29e7");
        person.delete(new UpdateListener(){
            @Override
            public void done(BmobException e) {
                if(e==null){
                    toast("删除成功:"+person.getUpdatedAt());
                }else{
                    toast("删除失败：" + e.getMessage());
                }
            }

        });
    };

    /**
     * 登录
     * @param username
     * @param password
     */
    private void login(String username,String password){
        final BmobUser bmobUser = new BmobUser();
        login_progress.setVisibility(View.VISIBLE);
        bmobUser.setUsername(username);
        bmobUser.setPassword(password);
        bmobUser.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser person, BmobException e) {

                if(e==null){
                    if(person!=null){

                    }
                    toast("登录成功");
                }else if (e.getErrorCode()==101){
                    toast("账号密码错误");
                }
                login_progress.setVisibility(View.GONE);
            }
        });
    }


    /**
     * 注册
     */
    private void registerData() {
        BmobUser bmobUser =new BmobUser();
        bmobUser.setUsername("tanghisishi");
        bmobUser.setPassword("13246548");
        bmobUser.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser o, BmobException e) {
                    if(e==null){
                        toast("注册成功");
                    }else  if(e.getErrorCode()==202){
                        toast("用户名已经存在");
                    }else {
                        toast("注册失败");
                    }
            }
        });
}
}

