package com.example.administrator.day03;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.day03.bean.MyUser;
import com.example.administrator.day03.bean.Person;
import com.example.administrator.day03.login.GoodActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobACL;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<Person> lppppppppp;
    listadapter listadapter;
    private SwipeRefreshLayout swipeRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lppppppppp = new ArrayList<>();
        initdata();
        initlist();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view1 = View.inflate(MainActivity.this, R.layout.addjor, null);
                final EditText editText = (EditText) view1.findViewById(R.id.ed111);
                final DatePicker datePicker = (DatePicker) view1.findViewById(R.id.datePicker);
                final EditText editText2 = (EditText) view1.findViewById(R.id.ed222);
                builder.setView(view1);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final Person p2 = new Person();
                        p2.setData(datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "--" + datePicker.getDayOfMonth());
                        p2.setMoo(editText.getText().toString().trim());
                        p2.setName(editText2.getText().toString().trim());
                        p2.setCreatename(MyUser.getCurrentUser().getUsername());
                     BmobFile bmobFile= (BmobFile) BmobUser.getObjectByKey("image");
                        BmobACL acl = new BmobACL();
                        acl.setPublicReadAccess(true);
                        acl.setWriteAccess(BmobUser.getCurrentUser(), true);
                        p2.setACL(acl);
                        p2.save(new SaveListener<String>() {
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                    lppppppppp.add(p2);
                                    listadapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(MainActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                builder.setCancelable(true);
                builder.show();
            }
        });
    }
    private void initdata() {
        new Thread() {
            @Override
            public void run() {
                BmobQuery<Person> qq = new BmobQuery<>();
                qq.findObjects(new FindListener<Person>() {
                    public void done(List<Person> list, BmobException e) {
                        for (Person ss : list) {
                            lppppppppp.add(ss);
                        }
                        listView.setAdapter(listadapter);
                        listadapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }
    private void initlist() {
        refresh();
        listView = (ListView) findViewById(R.id.list_itemee);
        listadapter = new listadapter(lppppppppp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    class listadapter extends BaseAdapter {
        List<Person> list;

        public listadapter(List<Person> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Viewholder viewholder;
            if (convertView == null) {
                viewholder = new Viewholder();
                convertView = View.inflate(MainActivity.this, R.layout.jor_item, null);
                viewholder.date = (TextView) convertView.findViewById(R.id.date);
                viewholder.designname = (TextView) convertView.findViewById(R.id.designname);
                viewholder.moo = (TextView) convertView.findViewById(R.id.moo);
                viewholder.imageButton = (ImageButton) convertView.findViewById(R.id.remove);
                convertView.setTag(viewholder);
            } else {
                viewholder = (Viewholder) convertView.getTag();
            }
            viewholder.date.setText(list.get(position).getData());
            viewholder.designname.setText(list.get(position).getName());
            viewholder.moo.setText(list.get(position).getMoo());
            if (MyUser.getCurrentUser().getUsername().equals(list.get(position).getCreatename())) {
                viewholder.imageButton.setVisibility(View.VISIBLE);

            } else {
                viewholder.imageButton.setVisibility(View.GONE);

            }
            viewholder.designname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos", position);
                    bundle.putString("name", lppppppppp.get(position).getCreatename());
                    Intent it = new Intent(getApplicationContext(), GoodActivity.class);
                    it.putExtras(bundle);
                    startActivity(it);
                }
            });
            viewholder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Person gameScore = new Person();
                    gameScore.setObjectId(list.get(position).getObjectId());
                    gameScore.delete(new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                new Thread() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                lppppppppp.remove(list.get(position));

                                                listadapter.notifyDataSetChanged();

                                            }
                                        });

                                    }
                                }.start();
                            } else {
                                Toast.makeText(MainActivity.this, "你没权限", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            return convertView;
        }

    }

    static class Viewholder {
        TextView date;
        TextView designname;
        ImageButton imageButton;
        TextView moo;
    }

    private void refresh() {
        swipeRefreshView = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshView);
        swipeRefreshView.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        swipeRefreshView.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // 开始刷新，设置当前为刷新状态
                //swipeRefreshLayout.setRefreshing(true);

                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // TODO 获取数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BmobQuery<Person> qq = new BmobQuery<>();
                        qq.findObjects(new FindListener<Person>() {
                            public void done(List<Person> list, BmobException e) {
                                if (lppppppppp != null) {
                                    lppppppppp.clear();
                                }
                                for (Person ss : list) {
                                    lppppppppp.add(ss);
                                }

                                listView.setAdapter(listadapter);
                                listadapter.notifyDataSetChanged();
                            }
                        });

                        Toast.makeText(MainActivity.this, "刷新了一条数据", Toast.LENGTH_SHORT).show();

                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        swipeRefreshView.setRefreshing(false);
                    }
                }, 1200);

                // System.out.println(Thread.currentThread().getName());

                // 这个不能写在外边，不然会直接收起来
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.exit:
                SharedPreferences sp = getSharedPreferences("oo", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.clear();
                edit.commit();
                BmobUser.logOut();
                finish();

               /* BmobQuery<Person> qq = new BmobQuery<>();
                qq.findObjects(new FindListener<Person>() {
                    public void done(List<Person> list, BmobException e) {
                        if (list != null) {
                            lppppppppp.clear();
                            for (Person ss : list) {
                                lppppppppp.add(ss);
                            }

                        }
                        else {
                            lppppppppp.clear();
                        }
                        listadapter.notifyDataSetChanged();
                    }
                });*/
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
