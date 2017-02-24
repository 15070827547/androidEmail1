package mi.email.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import mi.email.core.ResolveMail;
import mi.learn.com.R;

public class ReceiveList extends Activity {

	private static final String SAVE_INFORMATION = "save_information";

	private ListView listview;
	private int number;

	String Title;
	String Date;
	String From;
	String Content;
	String username;
	String password;
	String receivehost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);

		setContentView(R.layout.listmenu);
		listview = (ListView) findViewById(R.id.my_list);

		try {
			MenuList();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void MenuList() throws MessagingException, IOException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props); // 取得pop3协议的邮件服务器
		Store store = session.getStore("pop3");
		//连接pop.sina.com邮件服务器 //
		store.connect("pop.126.com","aa3068793203@126.com", "aa120112"); // 返回文件夹对象
		Folder folder = store.getFolder("INBOX"); // 设置仅读
		folder.open(Folder.READ_ONLY); // 获取信息
		Message message[] = folder.getMessages();

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();//定义一个List并且将其实例化
		for (int i = 0; i < message.length; i++) {//通过for语句将读取到的邮件内容一个一个的在list中显示出来
			ResolveMail receivemail = new ResolveMail((MimeMessage) message[i]);

			Title = receivemail.getSubject();//得到邮件的标题
			Date = receivemail.getSentDate();//得到邮件的发送时间
			Content= String.valueOf((CharSequence) message[i].getContent().toString());
			Toast.makeText(ReceiveList.this,Content,Toast.LENGTH_SHORT).show();
			HashMap<String, String> map = new HashMap<String, String>();//定义一个Map.将获取的内容以键值的方式将内容展现
			map.put("title", Title);//显示邮件的标题
			map.put("info", Date);//显示邮件的信息
			list.add(map);

			SimpleAdapter
					listAdapter = new SimpleAdapter(this, list,R.layout.item, new String[] { "title", "info" }, new int[] {
					R.id.title, R.id.info });
			listview.setAdapter(listAdapter);
		}

		folder.close(true);//用好之后记得将floder和store进行关闭
		store.close();

		// Item长按事件。得到Item的值，然后传递给MailDetail的值
		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("ID", position);
				intent.setClass(ReceiveList.this, MailDetails.class);
				startActivity(intent);
				return true;
			}
		});

	}
}
